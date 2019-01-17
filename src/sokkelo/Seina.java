package sokkelo;

/**
 * Sokkelon muodostavaa Seinaosaa mallintava luokka
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class Seina extends Juuri {
   
   /** Luokkavakiona seinän merkki */
   public static final char SEINA = '.';
   
   
   /*===========================================================================
    * Rakentajat
    *
    */
   public Seina(int rivi, int sarake) {
      super(rivi, sarake, SEINA);
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
      String luokannimi = muunnaKentaksi( getClass().getSimpleName() , NIMIKENTTA);
      return luokannimi + super.toString();
   }
   
}
