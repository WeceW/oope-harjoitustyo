package sokkelo;

/**
 * Sokkelon "liikkuvaa" ja energiallista sisältöä mallintava abstrakti luokka, josta
 * periytyy varsinaiset liikkuvat ja energialliset oliot kuten Mönkijä, Robotit ja Esineet
 * 
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public abstract class Sisalto extends Juuri implements Comparable<Sisalto> {
   
   
   /*===========================================================================
    * Attribuutit
    *
    */
   
   /** Sisältöelementin energiaa kuvaava attribuutti */
   private int energia;
   
   
   /*===========================================================================
    * Aksessorit
    *
    */
   
   public void energia(int e) {
      if (e > 0)
         energia = e;
   }
   
   public int energia() {
      return energia;
   }
   
   
   /*===========================================================================
    * Rakentajat
    *
    */
   
   
   /**
    * Kutsuu yliluokan rakentajaa, jossa asetetaan sijainti sekä merkki
    * ja asettaa itse energiatiedon oliolle
    * 
    * @param rivi
    * @param sarake
    * @param merkki
    * @param e, energia
    */
   public Sisalto(int rivi, int sarake, char merkki, int e) {
      super(rivi, sarake, merkki);
      energia(e);
   }
   
   
   /*===========================================================================
    * Object-luokan korvattavat metodit
    *
    */
   
   /** {@inheritDoc}
    * 
    * @return {@inheritDoc}
    */
   @Override
   public String toString() {
      String energia = muunnaKentaksi( energia() + "", KENTANKOKO );
      return super.toString() + energia;
   }
   
   
   
   /*===========================================================================
    * Comparable-rajapinnan toteutettavat metodit
    *
    */
   
   /**
    * Vertailee kahden olion energioita keskenään. 
    * Palauttaa vertailun tuloksen (-1, 0, 1) eli pienempi, yhtäsuuri tai suurempi
    * 
    * @param s, Sisalto-tyyppinen olio kuten Mönkijä, Robotti tai Esine
    * @return int, vertailun tulos
    */
   @Override
   public int compareTo(Sisalto s) {
         // Tämä olio < parametrina saatu olio.
      if (energia < s.energia())
         return -1;
      // Tämä olio == parametrina saatu olio.
      else if (energia == s.energia())
         return 0;
      // Tämä olio > parametrina saatu olio.
      else
         return 1;
   }
}
