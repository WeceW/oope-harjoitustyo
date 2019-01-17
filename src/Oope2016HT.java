import logiikka.Kayttoliittyma;

/**
 * Sokkeloseikkailu-pelin käynnistävä ajoluokka.
 * Tulostaa "tervehdyksen" ja kutsuu käyttöliittymää peliä pyörittämään
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class Oope2016HT {
   public static void main(String[] args) {
      
      // Luodaan uusi käyttöliittymä peliä varten
      Kayttoliittyma kayttis = new Kayttoliittyma();
      
      // Tulostetaan ohjelman/pelin nimi
      System.out.println("***********");
      System.out.println("* SOKKELO *");
      System.out.println("***********");
      
      // Kutsutaan käyttöliittymää suorittamaan varsinaista toimintaa
      kayttis.pelaa();
      
      // Tulostetaan lopetusviesti kun käyttöliittymä on lopettanut pelin pyörittämisen
      System.out.println("Ohjelma lopetettu.");

   }
}
