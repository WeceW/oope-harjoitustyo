package sokkelo;
import apulaiset.*;

/**
 * Sokkelossa liikkuvaa Robottia mallintava luokka
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class Robotti extends Sisalto implements Suunnallinen {
   
   /** Vakioitu robotin tunnus */
   public static final char ROBOTTI = 'R';
   
   /*===========================================================================
    * Attribuutit
    *
    */
   
   /** Kertoo robotin kulkusuunnan */
   private char suunta;
   
   
   
   /*===========================================================================
    * Aksessorit
    *
    */
   
   public void suunta(char s) throws IllegalArgumentException {
      if (s == POHJOINEN || s == LANSI || s == ETELA || s == ITA)
         suunta = s;
      else
         throw new IllegalArgumentException("VIRHE!");
   }
   
   public char suunta() {
      return suunta;  
   }

   
   
   /*===========================================================================
    * Rakentajat
    *
    */
   
   public Robotti(int rivi, int sarake, int energia, char s) {
      super(rivi, sarake, ROBOTTI, energia);
      suunta(s);
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
      String suunta = muunnaKentaksi( suunta() + "", KENTANKOKO);
      return luokannimi + super.toString() + suunta;
   }
   
   
}
