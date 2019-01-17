package sokkelo;
import logiikka.Sokkelo;
import apulaiset.Paikallinen;


/**
 * Sokkelon osia kuvaavan luokkahierarkian abstrakti juuri-luokka, josta periytyy
 * sisältöä ja rakenneosia kuvaavat luokat (kuten Seinat ja Kaytavät, sekä
 * käytävillä majailevat Mönkijä, Robotit ja Esineet).
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public abstract class Juuri implements Paikallinen {
   
   public static final char EROTIN = '|';
   public static final char VALI = ' ';
   public static final int KENTANKOKO = 4;
   public static final int NIMIKENTTA = 9;
   
   
   /*===========================================================================
    * Attribuutit
    *
    */
   
   /** Olion sijaintia kuvaava rivi-indeksi */
   private int rivi;
   
   /** Olion sijaintia kuvaava sarake-indeksi */
   private int sarake;   
   
   /** Olioa kuvaava merkki (esim. 'M' 'R' 'E' ' ' '.' */
   private char merkki;
   
   
   /*===========================================================================
    * Aksessorit
    *
    */
   
   public void rivi(int r) {
      if (r > 0)
         rivi = r;
   }
   
   public int rivi() {
      return rivi;
   }

   public void sarake(int s) {
      if (s > 0)
       sarake = s;
   }
   
   public int sarake() {
      return sarake;
   }

   public char merkki() {
      return merkki;
   }

   public void merkki(char m) {
      if (m != ' ')
         merkki = m;
   }
   
   
   /*===========================================================================
    * Rakentajat
    *
    */
   
   /** 
    * Kolmiparametrinen rakentaja, joka asettaa olion sijainnin sekä sen merkin
    * 
    * @param r rivi-indeksi
    * @param s sarake-indeksi
    * @param m kohdetta kuvaava merkki
    */
   public Juuri(int r, int s, char m) {
      rivi(r);
      sarake(s);
      merkki(m);
   }
   
   
   /*===========================================================================
    * Paikallinen-rajapinnan toteutettavat metodit
    *
    */
   
   
   /**
    * Päättelee on haluttu kohdenaapuri sallittu (eli käytävä)
    * 
    * @return true, jos paikka on sallittu (eli käytävä)
    */
   @Override
   public boolean sallittu() {
      // Luodaan uusi sokkelo
      Sokkelo sokkelo = new Sokkelo(false);
      Object[][] osat = sokkelo.osat();
      
      // Jos edessä käytävää, liikkuminen onnistuu
      if ( osat[ rivi() ][ sarake() ] instanceof Kaytava)
         return true;
      else  // Jos seinä, ei voida liikkua
         return false;
   }
   

   
   /*===========================================================================
    * Object-luokan korvattavat metodit
    *
    */
   

   
   /**
    * Muuntaa olion tiedot merkkijonoksi ja kutsuu yksittäiset kentät
    * muotoilevaa metodia
    * 
    * @return String, olion merkkijono
    */
   @Override
   public String toString() {
      String rivi = muunnaKentaksi( rivi() + "", KENTANKOKO );
      String sarake = muunnaKentaksi( sarake() + "" , KENTANKOKO);
      return rivi + sarake;
   }
   
   
   /*===========================================================================
    * Oliometodit
    *
    */
   
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
   public String muunnaKentaksi(String kentta, int pituus) {
      // Lisätään välilyöntejä
      if (kentta != null && pituus > 0) 
         while (kentta.length() < pituus) 
            kentta = kentta + VALI;
      // Lisätään kentän loppuun vielä putkimerkki
      kentta = kentta + EROTIN;
      // Palautetaan merkkijono
      return kentta;
   }
   
}