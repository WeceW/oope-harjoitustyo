# oope-sokkeloseikkailu
Sokkeloseikkailu-peli. Olio-ohjelmoinnin perusteet -kurssin harjoitustyö (2016)

Ohjelma on jaettu neljään pakkaukseen, joista kaksi on valmiina saadut apulaiset- sekä lista-pakkaus. Näiden lisäksi on logiikka-pakkaus joka pitää sisällään käyttöliittymän sekä pelin logiikasta vastaavan Sokkelo-luokan. Lisäksi pakkaukseen on liitetty OmaLista-luokka joka periytyy lista-pakkauksen LinkitettyLista-luokasta. Sokkelo-pakkaus sisältää varsinaista pelikenttää, eli sokkeloa ja sen osia kuvaavia luokkia. Sokkelon kaikki osat periytyy abstraktista juuri-luokasta, josta peritään suoraan rakenneosat eli seinät ja käytävät. Käytäväpaikoilla mahdollisesti sijaitsevat liikkuvat osat (Monkija, Robotti, Esine) peritään juuri-luokasta periytyvästä Sisalto-luokasta. Käyttöliittymä vastaa pelin ja pelaajan välisestä vuorovaikutuksesta ja välittää komentoja Sokkelo-luokalle jonka vastuulle kuuluu pääasiassa kaikki toiminnallisuuteen liittyvät toiminnot, jotka ohjailevat sokkelo-pakkauksen luokkien pohjalta luotuja olioita. Tallennus- ja lataustoiminnon on sijoitettu Sokkelo-luokan loppuun ns. omaksi kokonaisuudekseen. 

**Komentorivipohjainen peli hyväksyy seuraavat komennot:**
kartta
katso p
katso i
katso e
katso l
liiku p
liiku i
liiku e
liiku l
odota
inventoi
muunna n (n=1..)
tallenna
lataa
lopeta

*(p=pohjoinen | i=itä | e=etelä | l=länsi)*
