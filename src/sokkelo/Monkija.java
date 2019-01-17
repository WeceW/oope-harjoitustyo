package sokkelo;
import logiikka.OmaLista;
import apulaiset.*;
import fi.uta.csjola.oope.lista.*;

/**
 * Sokkelossa pelaajan liikuttelemaa Mönkijää mallintava luokka
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class Monkija extends Sisalto implements Suunnallinen {
   
   /** Vakioitu mönkijän tunnus */
   public static final char MONKIJA = 'M';
   
   /*===========================================================================
    * Attribuutit
    *
    */
   
   private char suunta;                // Mönkijä tietää suuntansa
   private int[] naapuri = new int[2]; // Naapuripaikan määrittelevä muuttuja
   private OmaLista varasto;                   // Mönkijän varasto esineille
   
   
   /*===========================================================================
    * Aksessorit
    *
    */
   
   public void suunta(char s) {
      if (s == POHJOINEN || s == LANSI || s == ETELA || s == ITA)
         suunta = s;
   }
   
   public char suunta() {
      return suunta;  
   }
   
   public void naapuri(int r, int s) {
      if (r >= 0 && s >= 0)
         naapuri[0] = r;
         naapuri[1] = s;
   }
   
   public int[] naapuri() {
      return naapuri;
   }
   
   public OmaLista varasto() {
      return varasto;
   }
   

   /*===========================================================================
    * Rakentajat
    *
    */
   
   public Monkija(int rivi, int sarake, int energia, char s) {
      super(rivi, sarake, MONKIJA, energia);
      suunta(s);
      varasto = new OmaLista();
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
