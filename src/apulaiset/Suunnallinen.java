package apulaiset;

/**
  * Rajapinta suunnallisille olioille. Olio tuntee pääilmansuunnat, joiden symbolit
  * on annettu vakioina.
  * <p>
  * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
  * <p>
  * @author Jorma Laurikkala (jorma.laurikkala@uta.fi),
  * Informaatiotieteiden yksikkö, Tampereen yliopisto.
  *
  */

public interface Suunnallinen {

   /** Pohjoista symboloiva merkki. */
   public static char POHJOINEN = 'p';

   /** Itää symboloiva merkki. */
   public static char ITA = 'i';

   /** Etelää symboloiva merkki. */
   public static char ETELA = 'e';

   /** Länttä symboloiva merkki. */
   public static char LANSI = 'l';

   /** Olion suunnan palauttava metodi.
     *
     * @return jokin yllä määritellyistä neljästä pääilmansuunnan symbolista.
     */
   public abstract char suunta();

   /** Olion suunnan asettava metodi.
     *
     * @param ilmansuunta uusi suunta, joka on jokin neljästä pääilmansuunnasta.
     * @throws IllegalArgumentException jos parametri ei ollut jokin yllä
     * määritellyistä pääilmansuunnan symboleista.
     */
   public abstract void suunta(char ilmansuunta) throws IllegalArgumentException;
}
