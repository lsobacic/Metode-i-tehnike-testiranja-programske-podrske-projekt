# Metode-i-tehnike-testiranja-programske-podrske-projekt

## Opis Projekta
Ovaj projekt predstavlja automatizirani testni okvir razvijen u Javi, koristeći Selenium WebDriver i TestNG. Namijenjen je za testiranje funkcionalnosti web aplikacije QA Practice(https://qa-practice.netlify.app), koja služi kao platforma za učenje i vježbanje različitih tehnika testiranja.

Projekt se sastoji od 6 automatiziranih testnih slučajeva koji pokrivaju ključna područja interakcije s web elementima, poput radio gumbova, rada sa formama, itd.


## Korišteni Alati i Tehnologije
Osnovu projekta čini programski jezik Java, dok se za automatizaciju interakcije s web preglednikom oslanja na Selenium WebDriver. Struktura i izvršavanje testnih slučajeva organizirani su pomoću TestNG okvira, a za upravljanje projektom i njegovim ovisnostima zadužen je Apache Maven. Razvojno okruženje u kojem je kod napisan je IntelliJ Community Edition, uz ručno konfiguriran ChromeDriver za komunikaciju s preglednikom.

## Obuhvaćeni Testni Scenariji

Checkboxes: Provjera označavanja, odznačavanja i resetiranja stanja.

Radio Buttons: Provjera međusobnog isključivanja i stanja onemogućenih gumba.

Dropdowns: Selekcija opcije iz padajućeg izbornika.

Register Form: Negativan test koji provjerava prikaz validacijske poruke za izostanak vrijednosti u obaveznim poljima.

Calendars: Odabir datuma iz osnovnog kalendara.

Products List: Dodavanje više proizvoda u košaricu i provjera izračuna ukupne cijene.
