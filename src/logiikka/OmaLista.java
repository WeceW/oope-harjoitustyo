package logiikka;

import fi.uta.csjola.oope.lista.LinkitettyLista;

/**
 * Tietorakenteena käytettävä lista
 *
 * @author Toni Weckroth, 422666 (weckroth.toni.j@student.uta.fi)
 * Olio-ohjelmoinnin perusteet, harjoitustyö
 */
public class OmaLista extends LinkitettyLista {
   

   /**
    * Lisätään tavaraa listalle nousevassa järjestyksessä
    * Kutsuva metodi tekee vertailun uuden ja ko. paikassa olevan kohteen välillä
    *
    * Lisätään kohde listalle vertaitavan alkion paikalle, 
    * jos se on pienempi tai yhtäsuuri kuin vertailtava alkio
    * 
    * @param obj Lisättävä kohde
    * @param vertailu Vertailun tulos (muotoa -1, 0, 1)
    * @param paikka Vertailun kohteena olevan alkion nykyinen paikka
    * @return lisaysOK (boolean), onnistuiko lisäys
    */
   public boolean lisaa(Object obj, int vertailu, int paikka) {
      boolean lisaysOK = false;  // Onnistuiko lisäys
         //while (!lisaysOK)       // Jatketaan kunnes lisäys tehty
            // Jos lisättävä kohde <= paikassa oleva alkio
            if (vertailu <= 0)
               lisaysOK = lisaa(paikka, obj); 
            // Jos lisättävä esine > laskurissa oleva alkio (esine)...
            // ...ja jos varaston viimeinen alkio
            else if (paikka == koko() - 1) { 
               lisaaLoppuun(obj);
               lisaysOK = true;
            } // ..tai jos varastossa on vielä lisää alkioita
            else
               lisaysOK = false;
      return lisaysOK;
   }
}
