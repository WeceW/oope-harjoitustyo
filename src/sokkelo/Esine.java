package sokkelo;

/**
 * Sokkelossa sijaitsevaa esinettä mallintava luokka
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class Esine extends Sisalto {
   
   /** Vakioitu esineen tunnus */
   public static final char ESINE = 'E';
   

   /*===========================================================================
    * Rakentajat
    *
    */
   
   public Esine(int r, int s, int e) {
      super(r, s, ESINE, e);
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