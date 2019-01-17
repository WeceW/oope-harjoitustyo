package sokkelo;
import logiikka.OmaLista;
import fi.uta.csjola.oope.lista.*;

/**
 * Sokkelon muodostavaa Käytävää mallintava luokka
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class Kaytava extends Juuri {
   
   /** Vakioitu käytävän merkki */
   public static final char KAYTAVA = ' ';
   
   
   /*===========================================================================
    * Attribuutit
    *
    */
   
   /** Käytävällä olevan sisällö säilövä lista */
   private OmaLista sisalto;
   
   
   
   /*===========================================================================
    * Aksessorit
    *
    */
   
   public OmaLista sisalto() {
      return sisalto;
   }
   
   
   /*===========================================================================
    * Rakentajat
    *
    */

   public Kaytava(int rivi, int sarake) {
      super(rivi, sarake, KAYTAVA);
      sisalto = new OmaLista();
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
