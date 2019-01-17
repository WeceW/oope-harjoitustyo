package logiikka;


import apulaiset.*;
import fi.uta.csjola.oope.lista.*;
import java.io.*;
import sokkelo.*;


/**
 * Sokkelo-pelin logiikasta vastaava luokka jonka toimintoja kutsutaan
 * käyttöliittymä-luokasta
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class Sokkelo {
   
   /** Vakioitu virheilmoitus */
   public static final String VIRHE = "Virhe!";
   
   
   /*===========================================================================
    * Attribuutit
    *
    */
   
   /** Pelialueen rivien lukumäärä */
   private int rivit;     
   
   /** Pelialueen sarakkeiden lukumäärä */
   private int sarakkeet;     
   
   /** Pelialueen seinät ja käytävät (sisältöineen) tallennetaan taulukkoon */
   private Object[][] osat;   
   
   /** Pelaajan ohjaama mönkijä-olio */
   private Monkija monkija;   
   
   /** Robotit kokoava lista */
   private OmaLista robotit;  
   
   /** Robotteja liikuttava automaatti */
   private Automaatti automaatti;     
   
   /** Robotteja liikuttavan automaatin alustamiseen käytettävä siemenluku */
   private int siemen;        
   
   
   /*===========================================================================
    * Aksessorit
    *
    */
   
   public Object[][] osat() {
      return osat;
   }
   
   
   /*===========================================================================
    * Rakentajat
    *
    */
   
   /**
    * Rakentaja saa parametrina mahdollisen alustuskäskyn.
    * Pelin aloitus ja tiedoston lataaminen alustaa automaatin.
    * ("sallittu"-metodin luoman apusokkelon yhteydessä ei alusteta automaattia)
    * 
    * @param alusta, tosi jos kutsuva metodi on antanut automaatin alustuskäskyn
    */
   public Sokkelo(boolean alusta) {
      lataa(alusta);
   }
   
   
   
   /*===========================================================================
    * Käyttöliittymästä kutsuttavat julkiset metodit
    *
    */

   
   /**
    * Lopetetaan peli. Palauttaa tiedon lopettamisesta kutsuvaan metodiin
    * eli käyttöliittymään
    * 
    * @return true, jos saatu lopetuskäsky (esim. taistelun häviäminen)
    */
   public boolean lopeta() {
      tulostaKartta();
      return true;
   }
   

   
   /**
    * Pelialueen kartan tulostava operaatio
    * 
    */
   public void tulostaKartta() {
      for (int i = 0; i < osat.length; i++) {
         for (int j = 0; j < osat[i].length; j++) {
            // Liitetään viite taulukon (pelialueen) silmukoitavaan osaan
            Object osa = osat[i][j];
            if (osa instanceof Kaytava) {
               // Liitetään käytävä-tyyppinen viite silmukoitavaan osaan
               Kaytava kaytava = (Kaytava)osa;
               
               char tulostettavaMerkki = ' ';
               
               // Jos käytävällä on sisältöä tulostetaan oikea merkki
               // 1. Mönkijä / 2. Robotti / 3. Esine, muutoin pelkkä Käytävä ' '
               if (kaytava.sisalto().koko() != 0) {
                  // Silmukoidaan käytävän sisältö läpi
                  for (int x = 0; x < kaytava.sisalto().koko(); x++) {
                     //Liitetään sisältö-tyyppinen viite silmukoitavaan osaan 
                     Sisalto sisalto = (Sisalto)kaytava.sisalto().alkio(x);
                     if (sisalto.merkki() == Monkija.MONKIJA)
                        tulostettavaMerkki = sisalto.merkki();
                     else if (tulostettavaMerkki != Monkija.MONKIJA)
                        if (sisalto.merkki() == Robotti.ROBOTTI)
                           tulostettavaMerkki = sisalto.merkki();
                        else if (tulostettavaMerkki != Robotti.ROBOTTI) {
                           tulostettavaMerkki = sisalto.merkki();
                        }
                  }
                  // Tulostetaan oikea merkki
                  System.out.print(tulostettavaMerkki);
               }
               
               else  // Jos ei erityistä sisältöä niin tulostetaan käytävän merkki
                  System.out.print(Kaytava.KAYTAVA);
            }
            else  // Jos ei käytävä niin seinä
               System.out.print(Seina.SEINA);
         }
      System.out.println();  // Vaihdetaan riviä
      }
   }
   
   
   
   /**
    * Tulostaa mönkijän tiedot varastoineen
    * 
    */
   public void inventoi() {
      
      // Tulostetaan ensin mönkijän tiedot
      System.out.println(monkija.toString());
      
      // Apumuuttuja varastolle
      OmaLista varasto = monkija.varasto();
      
      if (!varasto.onkoTyhja()) // Jos varastossa on tavaraa
         // Silmukoidaan varasto läpi ja tulostetaan sen säilömien esineiden tiedot
         for (int i = 0; i < varasto.koko(); i++) 
            System.out.println(varasto.alkio(i).toString());
   }
   
   
   
   /**
    * Muuntaa mönkijän varastossa olevien esineiden energiaa mönkijän käyttöön
    * 
    * @param param kappalemäärä (montako esinettä muunnetaan)
    */
   public void muunna(char param) {
      try {
         // Muutetaan parametrina saatu merkki kokonaisluvuksi
         int kpl = Integer.parseInt("" + param);
         
         // Apumuuttuja mönkijän varastolle
         OmaLista varasto = monkija.varasto();
         
         // Tarkastetaan, että syötetty luku <= varaston koko
         if (kpl <= varasto.koko())
            for (int i = 0; i < kpl; i++) {
               // Asetetaan apuviite varaston ensimmäisen alkioon
               Esine alkio = (Esine)varasto.alkio(0);
               // Lisätään alkion (esineen) energiavarat mönkijän käyttöön
               monkija.energia(monkija.energia() + alkio.energia());
               // Poistetaan käytetty esine varastosta
               varasto.poistaAlusta();
            }
         else  // Jos syötetty kappalemäärä suurempi kuin varaston koko
            System.out.println(VIRHE);
      }  // Syötetty muuta kuin numero parametrina
      catch (NumberFormatException e) {
         System.out.println(VIRHE);
      }
   }
   
   
   
   /**
    * Katsoo halutussa suunnassa olevan naapurialkion sisältö
    * 
    * @param suunta, katsottava suunta saadaan käyttäjältä parametrina
    * @throws IllegalArgumentException jos virheellinen parametri (suunta)
    */
   public void katso(char suunta) throws IllegalArgumentException {
      
      if (suunta == Suunnallinen.POHJOINEN || suunta == Suunnallinen.ITA ||
          suunta == Suunnallinen.ETELA || suunta == Suunnallinen.LANSI) {
         
         // Päivitetään mönkijän naapuriksi määrätyssä suunnassa oleva alkio
         paivitaNaapuri(suunta);

         // Apumuuttuja naapurialkiolle joka poimitaan sokkelon osia kuvaavasta taulukosta
         Object naapuri = osat[ monkija.naapuri()[0] ][ monkija.naapuri()[1] ];

         // Kutsutaan parametrina annettavan naapurialkion tulostavaa metodia
         tulostaRakenne(naapuri);
      }
      // Heitetään virhe jos parametrina saatu suunta oli virheellinen
      else throw new IllegalArgumentException();
      
   }
   
   
   
   /**
    * Liikkumista organisoiva metodi, päättelee suunnan 
    * ja kutsuu varsinaisen siirron tekevää siirry-metodia
    * 
    * @param suunta mönkijän menosuunta
    * @return true, jos saatu liikkumisen seurauksena lopetuskäsky
    * @throws IllegalArgumentException jos virheellinen parametri (suunta)
    */
   
   public boolean liiku(char suunta) throws IllegalArgumentException {
      
      boolean lopeta = false; // Muuttuja kertoo onko aika lopettaa peli

      if (suunta == Suunnallinen.POHJOINEN || suunta == Suunnallinen.ITA ||
          suunta == Suunnallinen.ETELA || suunta == Suunnallinen.LANSI) {
         
         paivitaNaapuri(suunta);
         Paikallinen naapuri = (Paikallinen)osat[ monkija.naapuri()[0] ][ monkija.naapuri()[1] ];

         // Jos haluttu suunta on sallittu (eli käytävä eikä seinä)
         if ( naapuri.sallittu() ) {
            // Siirretään mönkijää ja tutkitaan uusi paikka
            siirry(suunta);
            lopeta = tutkiPaikka(); // saadaanko lopeta-komento (=taistelun häviäminen)
            if (!lopeta)
               if (skannaaKaytavat())
                  lopeta = true; // Jos esineet on loppu
               // Jos jäljellä vielä robotteja
               else if (robotit.alkio(0) != null) {
                  siirraRobotit();
                  // Tutkitaan paikka uudestaan sille tunkeilleiden robottien varalta
                  lopeta = tutkiPaikka();
                  // Jos esineet ja robotit ovat loppu, voidaan lopettaa peli
                  if (skannaaKaytavat() && robotit.alkio(0) == null)
                     lopeta = true;
               }
            tulostaKartta();
         }
         else {  // Jos vastassa on seinä
            System.out.println("Kops!");
            siirraRobotit();
            lopeta = tutkiPaikka();
            tulostaKartta();
         }

         return lopeta;
      }
      // Heitetään virhe jos parametrina saatu suunta oli virheellinen
      else throw new IllegalArgumentException();
   }
   

   
   /**
    * Ohittaa pelaajan vuoron, robotteja liikutetaan ja tulostetaan kartta
    * 
    * @return true, jos saatu lopetuskäsky (=hävitty taistelu)
    */
   public boolean odota() {
      boolean lopeta;
      if (!robotit.onkoTyhja())
         siirraRobotit();
      lopeta = tutkiPaikka();
      tulostaKartta();
      return lopeta;
   }
   
   
   

   
   
   /*====================================================================================
    * Luokan omaan käyttöön tarkoitetut yksityiset metodit
    *
    */
   
   
   
   /**
    * Tulostaa parametrina saamansa olion (Kaytava/Seina) tiedot
    * 
    * @param rakenne, seinää tai käytävää kuvaava olio
    */
   private void tulostaRakenne(Object rakenne) {
      if (rakenne instanceof Kaytava) {
         // Liitetään käytävä-tyyppinen viite tutkittavaan kohteeseen
         Kaytava kaytava = (Kaytava)rakenne;
         // Tulostetaan ensin kaytavan tiedot
         System.out.println(kaytava.toString());
         
         // Jos sisällössä on tavaraa
         if (!kaytava.sisalto().onkoTyhja()) 
            // Silmukoidaan sisältö läpi ja tulostetaan listalla olevien asioiden tiedot
            for (int i = 0; i < kaytava.sisalto().koko(); i++) 
               System.out.println(kaytava.sisalto().alkio(i).toString());
      }
      else if (rakenne instanceof Seina)
         // Liitetään seinä-tyyppinen viite kohteeseen ja tulostetaan tiedot
         System.out.println( rakenne.toString() );
   }
   
   
   
   /**
    * Päivittää mönkijän attribuuttina olevan naapuripaikan 
    * käyttäjän syöttämän parametrin perusteella
    * 
    * @param suunta 
    */
   private void paivitaNaapuri(char suunta) {
      //Tarkistetaan, että suunta on jokin neljästä ilmansuunnasta
      if (suunta == Suunnallinen.POHJOINEN || suunta == Suunnallinen.ITA || 
          suunta == Suunnallinen.ETELA || suunta == Suunnallinen.LANSI) {
         
         if (suunta == Suunnallinen.POHJOINEN)
            monkija.naapuri( monkija.rivi() - 1 , monkija.sarake() );
         else if (suunta == Suunnallinen.ITA)
            monkija.naapuri( monkija.rivi() , monkija.sarake() + 1 );
         else if (suunta == Suunnallinen.ETELA)
            monkija.naapuri( monkija.rivi() + 1 , monkija.sarake() );
         else if (suunta == Suunnallinen.LANSI)
            monkija.naapuri( monkija.rivi() , monkija.sarake() - 1 );
      }
      else  // Jos suuntaa kuvaava parametri on virheellinen
         System.out.println(VIRHE);
   }

   
   
   /**
    * Metodi huolehtii robittien liikuttelusta.
    * Käyttää apunaan Automaatti-luokkaa joka arpoo robotin uuden paikan
    * 
    */
   private void siirraRobotit() {
      // Poistetaan ensin robotit käytäväpaikoiltaan...
      siivoaRobotit();
      
      // Kutsutaan robottien paikat päivittävää automaattia...
      automaatti.paivitaPaikat(robotit, osat());
      
      // ...ja poistetaan robotit vielä uudestaan käytäväpaikoiltaan...
      siivoaRobotit();
      
      // ...ja lisätään ne uusille Automaatin arpomille paikoille
      for (int i = 0; i < robotit.koko(); i++) {
         Sisalto robotti = (Sisalto)robotit.alkio(i);
         Kaytava robotinPaikka = (Kaytava)osat[ robotti.rivi() ] [ robotti.sarake() ];
         OmaLista sisalto = (OmaLista)robotinPaikka.sisalto();
         boolean lisaysOK = false;  // Onnistuiko lisäys
         
         // Jos käytävällä ei ole sisältöä
         if (sisalto.koko() == 0)
            sisalto.lisaaLoppuun(robotti);
         else // Jos on sisältöä, lisätään nousevassa järjestyksessä
            for (int j = 0; j < sisalto.koko(); j++) {
               while (!lisaysOK && j < sisalto.koko()) {  // Vertaillaan alkioita kunnes lisäys on ok
                  int vertailu = robotti.compareTo( (Sisalto)sisalto.alkio(j) );
                  // Kutsutaan lisäyksen oikeaan paikkaan tekevää OmaLista-luokan metodia
                  lisaysOK = sisalto.lisaa(robotti, vertailu, j++);
               }
            }
      }
   }



   
   /**
    * Siivoaa robotit, eli poistaa ne (vanhoilta paikoiltaan)
    * 
    */
   private void siivoaRobotit() {
      // Poistetaan robotit sen hetkisiltä käytäväpaikoilta
      for (int i = 0; i < osat.length; i++)
         for (int j = 0; j < osat[i].length; j++)
            if (osat[i][j] instanceof Kaytava) {
               Kaytava kaytava = (Kaytava)osat[i][j];
               for (int x = 0; x < kaytava.sisalto().koko(); x++)
                  if( kaytava.sisalto().alkio(x) instanceof Robotti )
                     kaytava.sisalto().poista(x);
            }
   }
   
   
   
   /**
    * Metodi siirtää mönkijää yhden paikan verran haluttuun ilmansuuntaan
    * 
    * @param suunta (menosuunta)
    */
   private void siirry(char suunta) {
      
      // Sijoitetaan kohdepaikan paikkatiedot rivi- & sarakemuuttujiin
      int[] kohde = monkija.naapuri();
      int kohdeRivi = kohde[0];  int kohdeSarake = kohde[1];

      // Määritellään nykyinen paikka ja uusi paikka
      Kaytava nykyinenPaikka = (Kaytava)osat[ monkija.rivi() ][ monkija.sarake() ];
      Kaytava uusiPaikka = (Kaytava)osat[kohdeRivi][kohdeSarake];

      // Vaihdetaan uusi paikka viittaamaan mönkijään 
      // ja poistetaan viittaus vanhasta paikasta
      uusiPaikka.sisalto().lisaaAlkuun(monkija);
      nykyinenPaikka.sisalto().poistaAlusta();
      // Muutetaan mönkijän paikkatiedot vastaamaan todellisuutta
      monkija.rivi(kohdeRivi);
      monkija.sarake(kohdeSarake);
      monkija.suunta(suunta);
      
      // Muutetaan myös Mönkijän varastossa (mahdollisesti) olevien esineiden sijainti
      if (!monkija.varasto().onkoTyhja())
         for (int i = 0; i < monkija.varasto().koko(); i++) {
            Esine esine = (Esine)monkija.varasto().alkio(i);
            esine.rivi(kohdeRivi);
            esine.sarake(kohdeSarake);
         }
   }
   
   
   
   /**
    * Metodi tutkii paikan johon mönkijä on siirtynyt
    * Kerätään vastaantulevat esineet ja tarvittaessa taistellaan robottia vastaan
    * 
    * @return true, jos saatu lopetuskäsky (=hävitty taistelu)
    */
   private boolean tutkiPaikka() {
      boolean lopeta = false;
      // Määritellään tutkittava käytäväpaikka (ja lista/sisältö)
      // mönkijän nykyisen sijainnin perusteella
      Kaytava tutkittavaPaikka = (Kaytava)osat[ monkija.rivi() ][ monkija.sarake() ];
      OmaLista sisalto = tutkittavaPaikka.sisalto();
      
      // Jos käytävällä on tavaraa
      if (!sisalto.onkoTyhja())
         for (int i = 0; i < sisalto.koko(); i++)
            // Poistetaan ESINE käytävältä ja lisätään se varastoon
            if (sisalto.alkio(i) instanceof Esine) {
               lisaaVarastoon( (Esine)sisalto.poista(i) );
               i--;
            }
            // Taistellaan, jos kohdattiin robotti
            else if (sisalto.alkio(i) instanceof Robotti) {
               // Jos tuli tappio, lopetetaan
               lopeta = !taistele( (Robotti)sisalto.alkio(i), i ); 
               i--;
            }
      
      return lopeta; 
   }
   
   
   
   /**
    * Mönkijän ja robotin välinen taistelu
    * 
    * @param r, robotti
    * @param alkio, robotin indeksi käytävän sisältölistalla
    * @return true, jos voitettiin taistelu
    */
   private boolean taistele(Robotti r, int alkio) {
      
      if (monkija.compareTo(r) >= 0) {
         System.out.println("Voitto!");
         monkija.energia( monkija.energia() - r.energia() );
         
         // Poistetaan robotti pelikentältä
         Kaytava kaytava = (Kaytava)osat[r.rivi()][r.sarake()];
         OmaLista sisalto = kaytava.sisalto();
         sisalto.poista(alkio);
         
         // Poistetaan kyseinen robotti robotit-listalta
         for (int i = 0; i < robotit.koko(); i++)
            if (robotit.alkio(i).equals(r))
               robotit.poista(i);

         return true; // Palautetaan voitto
      }
      else {
         System.out.println("Tappio!");
         Kaytava kaytava = (Kaytava)osat[monkija.rivi()][monkija.sarake()];
         OmaLista sisalto = kaytava.sisalto();
         sisalto.poistaAlusta();
         return false; // Ei tullut voittoa
      }
   }
   
   
   
   /**
    * Lisätään esineitä varastoon nousevassa järjestyksessä
    * 
    * @param e varastoon lisättävä esine
    */
   private void lisaaVarastoon(Esine e) {
      OmaLista varasto = monkija.varasto();
      
      // Lisätään suoraan varastoon jos varasto on tyhjä
      if (varasto.onkoTyhja())
         varasto.lisaaAlkuun(e);
      
      else {  // Jos varastossa on jo tavaraa
         boolean lisaysOK = false;  // Onnistuiko lisäys
         for (int i = 0; i < varasto.koko(); i++) {
            while (!lisaysOK && i < varasto.koko()) {  // Vertaillaan alkioita kunnes lisäys on ok
               int vertailu = e.compareTo( (Esine)varasto.alkio(i) );
               // Kutsutaan lisäyksen oikeaan paikkaan tekevää OmaLista-luokan metodia
               lisaysOK = varasto.lisaa(e, vertailu, i++);
            }
         }
      }
   }

   
   
   /**
    * Skannataan käytävät läpi ja selvitetään onko esineitä vielä jäljellä
    * 
    * @return true, jos käytävät ovat tyhjänä esineistä
    */
   private boolean skannaaKaytavat() {
      // Etsitään esineitä. Jos löytyy, jatketaan peliä, muutoin lopetetaan
      boolean kaytavatTyhjia = true;
      
      for (int i = 0; i < osat.length; i++)
         for (int j = 0; j < osat[i].length; j++)
            if (osat[i][j] instanceof Kaytava) {
               Kaytava kaytava = (Kaytava)osat[i][j];
               OmaLista sisalto = kaytava.sisalto();
               
               if (sisalto != null) 
                  for (int x = 0; x < sisalto.koko(); x++)
                     if ( sisalto.alkio(x) instanceof Esine )
                        kaytavatTyhjia = false; // Esine löytyi
            }
      return kaytavatTyhjia; 
   }
   
   
   
   
   /*====================================================================================
    * Tallennuksen ja latauksen suorittavat metodit
    *
    */
   

   
   /**
    * Tallentaa sokkelon txt-tiedostoon
    * 
    */
   public void tallenna() {
      try {
         File tiedosto = new File("sokkelo.txt");
         FileOutputStream tulostusvirta = new FileOutputStream(tiedosto);
         PrintWriter kirjoittaja = new PrintWriter(tulostusvirta, true);
         
         // Luetaan ensimmäinen rivi ja kirjoitetaan se tiedostoon
         String ekarivi = tallennaEkarivi();
         kirjoittaja.println(ekarivi);
         
         // Luetaan ja kirjoitetaan sokkelon rakenneosien tiedot
         for (int i = 0; i < osat.length; i++)
            for (int j = 0; j < osat[i].length; j++) 
               
               // Jos kyseessä käytäväpaikka, tutkitaan tarkemmin sen sisältöä
               if (osat[i][j] instanceof Kaytava) {
                  // Liitetään käytävä-tyyppinen viite tutkittavaan kohteeseen
                  Kaytava kaytava = (Kaytava)osat[i][j];
                  // Tulostetaan tiedostoon ensin kaytavan tiedot
                  kirjoittaja.println(kaytava.toString());
                  
                  // Jos käytävän sisällössä on tavaraa
                  if (!kaytava.sisalto().onkoTyhja()) {
                     OmaLista sisalto = (OmaLista)kaytava.sisalto();
                     for (int s = 0; s < sisalto.koko(); s++) {
                        kirjoittaja.println(sisalto.alkio(s).toString());
                        
                        // Jos Mönkijä, tallennetaan myös varasto
                        if (sisalto.alkio(s) instanceof Monkija)
                           if (!monkija.varasto().onkoTyhja()) // Jos varastossa on tavaraa
                              // Silmukoidaan varasto läpi ja tulostetaan sen säilömien esineiden tiedot
                              for (int v = 0; v < monkija.varasto().koko(); v++) 
                                 kirjoittaja.println(monkija.varasto().alkio(v).toString());
                     }
                  }
               } // Jos kyseessä on seinä, tulostetaan seinän tiedot
               else if (osat[i][j] instanceof Seina)
                  kirjoittaja.println( osat[i][j].toString() );

         // Suljetaan kirjoittaja
         kirjoittaja.close();
      } 
      catch (IOException e) {
         System.out.print("Kirjoitusvirhe!");
      }
   }
   
   
   
   /**
    * Metodi hankkii tallennettavaan tiedostoon ensimmäisen rivin tiedot
    * ja palauttaa ne muotoiltuna (siemen, rivit, sarakkeet)
    * 
    * @return ekarivi
    */
   private String tallennaEkarivi() {
         
         String siemenluku = muunnaKentaksi(siemen + "", 4); 
         String rivitLkm = muunnaKentaksi(rivit + "", 4);
         String sarakeLkm = muunnaKentaksi(sarakkeet + "", 4);
         
         String ekarivi = siemenluku + rivitLkm + sarakeLkm;
         return ekarivi;
   }
   
   
   /**
    * Metodi muotoilee sille (String-muodossa) annetut arvot siten, että
    * kentän pituus pysyy vakiona. Myös pituus saadaan parametrina.
    * Kentän alkuun sijoitetaan parametrina saatu merkkijono ja loppuosa
    * täytetään välimerkeillä. Viimeiseksi lisätään erotin.
    * 
    * @param kentta Kentän alkuun tuleva merkkijono
    * @param pituus Kentän pituus yhteensä välilyönteineen
    * @return String, muotoiltu merkkijono
    */
   private String muunnaKentaksi(String kentta, int pituus) {
      // Lisätään välilyöntejä
      if (kentta != null && pituus > 0) 
         while (kentta.length() < pituus) 
            kentta = kentta + ' ';
      // Lisätään kentän loppuun vielä putkimerkki
      kentta = kentta + '|';
      // Palautetaan merkkijono
      return kentta;
   }
   

   
   
   /**
    * Tiedoston lataava metodi
    * Ladataan txt-tiedostosta sokkelon tiedot
    * 
    * @param alusta true, jos kutsuva metodi haluaa robotteja liikuttelevan automaatin alustettavan
    */
   public void lataa(boolean alusta) {
      try {
         // Luodaan lukija tiedoston lukemista varten
         FileInputStream syotevirta = new FileInputStream("sokkelo.txt");
         InputStreamReader lukija = new InputStreamReader(syotevirta);
         BufferedReader puskuroituLukija = new BufferedReader(lukija);
         
         // Luetaan tiedoston ensimmäinen rivi
         if (puskuroituLukija.ready()) {
            String ekarivi = puskuroituLukija.readLine();
            lataaEkarivi(ekarivi);
            // Alustetaan automaatti jos saatu kutsuvalta metodilta käsky niin tehdä
            if (alusta) {
               automaatti = new Automaatti();
               automaatti.alusta(siemen);
            }
         }
         
         // Luodaan uusi 2-ulotteinen taulukko sokkelon osille, sekä lista roboteille
         osat = new Object[rivit][sarakkeet];
         robotit = new OmaLista();
         
         // Alustetaan mönkijä tyhjäksi uutta tiedostoa ladattaessa
         monkija = null;
         
         // Luetaan (ja ladataan) sokkelon sisältö tiedostosta
         while (puskuroituLukija.ready()) {
            String tekstirivi = puskuroituLukija.readLine();
            // Operaatio lataa tekstirivillä olevan olion pelialueelle
            lataaSisalto(tekstirivi);   
         }
         // Suljetaan lukija
         puskuroituLukija.close();
         
      }
      catch (FileNotFoundException e) {
         System.out.println("Tiedostoa ei löydy!");
      }
      catch (IOException e) {
         System.out.print("Lukuvirhe!");
      }
   }
   
   
   
   
   /**
    * Ladataan tiedoston ensimmäisen rivin tiedot ja tallennetaan ne olion attribuutteihin
    * 
    * @param ekarivi, tiedostosta luettu ensimmäinen rivi
    */
   private void lataaEkarivi(String ekarivi) {
      // Pilkotaan rivi osiin ja poistetaan välilyönnit
      String[] eka = ekarivi.split("[|]");
      for (int i = 0; i < eka.length; i++)
         eka[i] = eka[i].trim();
      
      // Liitetään tiedostosta ladatut tiedot sokkelon attribuutteihin
      if (eka.length == 3) {
         siemen = Integer.parseInt(eka[0]);
         rivit = Integer.parseInt(eka[1]);
         sarakkeet = Integer.parseInt(eka[2]);
      }
   }
   
   
   
   /** 
    * Ladataan olio tekstiriviltä osaksi pelialueen sisältöä
    * 
    * @param tekstirivi 
    */
   private void lataaSisalto(String tekstirivi) {

      // Pilkotaan rivi osiin ja poistetaan välilyönnit
      String[] olionOsat = tekstirivi.split("[|]");
      for (int i = 0; i < olionOsat.length; i++)
         olionOsat[i] = olionOsat[i].trim();
      
      // Muuttuja tekstirivistä pilkotuille tiedoille
      String tyyppi = olionOsat[0];
      int rivi = Integer.parseInt(olionOsat[1]);
      int sarake = Integer.parseInt(olionOsat[2]);
      int energia;   
      char suunta = ' ';

      // Luodaan oikeanlaiset oliot ja liitetään taulukkoon tai 
      // toisen olion listalle (esim. Mönkijän varasto tai käytävän sisältö)
      if (tyyppi.equals("Seina"))
         osat[rivi][sarake] = new Seina(rivi, sarake);
      else if (tyyppi.equals("Kaytava"))
         osat[rivi][sarake] = new Kaytava(rivi, sarake);
      else if (tyyppi.equals("Esine") || tyyppi.equals("Monkija") || tyyppi.equals("Robotti") ) {
         energia = Integer.parseInt(olionOsat[3]);
         Kaytava kaytava = (Kaytava)osat[rivi][sarake];
         
         // Jos esine, lisätään se joko mönkijän varastoon tai käytävälle
         if (tyyppi.equals("Esine")) {
            Esine esine = new Esine(rivi, sarake, energia);
               // Jos sama sijainti kuin mönkijällä, lisätään varastoon, muutoin käytävälle
               if (monkija != null && esine.rivi() == monkija.rivi() && esine.sarake() == monkija.sarake() )
                  lisaaVarastoon(esine);
               else
                  kaytava.sisalto().lisaaLoppuun(esine);
         }
         // Jos mönkijä, lisätään se käytävälle
         else if (tyyppi.equals("Monkija")) {
            suunta = olionOsat[4].charAt(0);
            monkija = new Monkija(rivi, sarake, energia, suunta);
            kaytava.sisalto().lisaaAlkuun( monkija );
         }
         // Jos robotti, lisätään se käytävälle ja robotit-listalle
         else if (tyyppi.equals("Robotti")) {
            suunta = olionOsat[4].charAt(0);
            Robotti robotti = new Robotti(rivi, sarake, energia, suunta);
            kaytava.sisalto().lisaaAlkuun(robotti);
            robotit.lisaaLoppuun(robotti);
         }
      }
   }
   
   

}