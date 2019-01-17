package logiikka;

import apulaiset.*;

/**
 * Sokkeloseikkailu-peliä pyörittävä käyttöliittymä, joka lukee käyttäjältä
 * komentoja ja kutsuu logiikkaluokan (Sokkelo) operaatioita niiden mukaisesti
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class Kayttoliittyma {   
   
   // Vakiot käyttäjältä luettavissa oleville komennoille
   public final String LOPETA = "lopeta";
   public final String LATAA = "lataa";
   public final String TALLENNA = "tallenna";
   public final String KARTTA = "kartta";
   public final String INVENTOI = "inventoi";
   public final String MUUNNA = "muunna"; 
   public final String KATSO = "katso";
   public final String LIIKU = "liiku";
   public final String ODOTA  = "odota";
   
   
   /*===========================================================================
    * Attribuutit
    *
    */
   
   /** Pelin logiikkaa pyörittävä olio */
   private Sokkelo sokkelo;
   
   
   /*===========================================================================
    * Julkiset metodit
    *
    */
   
   
   /** 
    * Varsinaista pelaamista pyörittävä operaatio.
    * Luetaan käyttäjän syötteitä ja toimitaan näiden komentojen mukaan
    *
    * Kutsutaan komentojen perusteella logiikkaluokan (Sokkelo)
    * toiminnasta vastaavia metodeja
    * 
    */
   public void pelaa() {
      sokkelo = new Sokkelo(true);  // Luodaan uusi sokkelo
      boolean lopeta = false;       // "lopeta" muuttaa tämän todeksi = peli loppuu
      
      while (!lopeta) {             // Luetaan syötteitä kunnes saadaan "lopeta"-komento
         // Luetaan käyttäjältä komento (ja mahdollinen parametri) muodossa "komento x"
         System.out.println("Kirjoita komento:");
         String syote = In.readString();
         
         // Muunnetaa syötteet erillisiksi merkkijonoiksi (komento & parametri)
         String[] komento = syote.split("[ ]");
         
         try {
            // Varmistetaan ettei komennot sisältävä taulukko ole tyhjä 
            // ja siinä on 1-2 komentoa (komento & mahdollinen parametri)
            if (komento != null && (komento.length >= 1 || komento.length >= 2)) {
               // PARAMETRITTOMAT KOMENNOT
               if (komento.length == 1) {
                  if (komento[0].equals(LOPETA))        // Lopeta
                     lopeta = sokkelo.lopeta();
                  else if (komento[0].equals(LATAA))    // Lataa
                     sokkelo.lataa(true);
                  else if (komento[0].equals(TALLENNA)) // Tallenna
                     sokkelo.tallenna();
                  else if (komento[0].equals(KARTTA))   // Tulosta pelialueen kartta
                     sokkelo.tulostaKartta();
                  else if (komento[0].equals(INVENTOI)) // Inventoi mönkijän varasto
                     sokkelo.inventoi();
                  else if (komento[0].equals(ODOTA))    // Ohittaa pelivuoron
                     lopeta = sokkelo.odota();
                  else // Virhe jos syote ei vastannut ohjelman hyväksymiä komentoja
                     System.out.println(sokkelo.VIRHE);
               }
               // PARAMETRILLISET KOMENNOT
               else if (komento.length == 2 && komento[1].length() == 1) {
                  char parametri = komento[1].charAt(0);
                  if (komento[0].equals(MUUNNA))   // Muunna energiaa varastosta käyttöön
                      sokkelo.muunna(parametri);
                  else if (komento[0].equals(KATSO))    // Katsominen eri ilmansuuntiin (katso x)
                     sokkelo.katso(parametri);
                  else if (komento[0].equals(LIIKU))    // Liikkuminen eri ilmansuuntiin (liiku x)
                     lopeta = sokkelo.liiku(parametri);
                  else // Virhe jos syote ei vastannut ohjelman hyväksymiä komentoja
                     System.out.println(sokkelo.VIRHE);
               }
            
               else // Virhe jos syote ei vastannut ohjelman hyväksymiä komentoja
                  System.out.println(sokkelo.VIRHE);
            }
            else   // Virhe jos käyttäjän syötteissä vikaa (taulukko = null)
               System.out.println(sokkelo.VIRHE);
         }
         catch (IllegalArgumentException e) {
            System.out.println(sokkelo.VIRHE);
         }
      }
   }

}