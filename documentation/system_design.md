# 1. A rendszer céljai
- A felhasználók hozzá tudjanak szólni a meglévő témákhoz, nyilvános beszélgetést folytatva
- A céges dolgozók új témákat tudjanak nyitni, és legyen módjuk moderálni a meglévőket egy tiszteletteljes beszélgetés folytatásának megtartása érdekében
- Privát üzenet funkciójával olyan beszélgetést biztosítani amelyet csak a két résztvevő felhasználó láthat (ennek megvalósítása az adott felhasználó adatlapján, az erre megfelelő gomb használatával eszközölhető)
- A témákhoz való hozzászólások esetén minden hozzászólás azonosítható legyen egy felhasználóval.
- A nem regisztrált felhasználók korlátozása (a fórumon kizárólag bejegyzések olvasásának lehetősége)
- A felhasználó tájékoztatása nem megfelelő adatok megadásáról a regisztráció során
- Egyéni profilkép feltöltésének lehetősége a felhasználói adatlapra
- A felhasználói adatok biztonságos tárolása

# 2. A rendszer nem céljai

- Teljeskörű hozzáférés biztosítása nem regisztrált felhasználók számára
- Körlevelek küldésének lehetősége
- "Like/Dislike" alapú szavazási rendszer kialakítása a hozzászólások megbízhatóságának megszavazása érdekében
- Csoportos privát beszélgetések létrehozása
- Saját felhasználói fiók törlése céges felhasználó közreműködése nélkül
- A felhasználó hozzászólásainak utólagos módosítása
- Kötelezni a felhasználót további adatok (például : teljes név, e-mail cím) megadására
- Biztosítani nem adminisztrátori hatáskörrel rendelkező felhasználó számára az adattörlés lehetőségét


# 3. Projektterv

&nbsp;&nbsp;&nbsp;Egy már meglévő webshop szolgáltatásait bővítjük egy fórummal, ami egy olyan platformot biztosít a meglévő, illetve új vásárlók számára, ahol hatékonyan kommunikálhatnak. A projekten négy fő dolgozik, megosztva a munkát. Az alkalmazás frontend és a backend részén is 2-2 fő dolgozik.  
&nbsp;&nbsp;&nbsp;A projekttagok minden héten megbeszélik a heti feladatokat és a hét végén történik a közös kód átnézése, felmerült kérdések, problémák, igények megbeszélése.  
&nbsp;&nbsp;&nbsp;A projekt nagyobb részfeladatokra kerül bontásra. Minden nagyobb részfeladatra 1 hét áll rendelkezésre, egyszerre mindenki csak 1 kis feladatot vállalhat el. A cél hogy a hét végére minden kitűzött feladat elvégzésre kerüljön.
&nbsp;&nbsp;&nbsp;Az elkészült terveket a terveken nem dolgozó csapattársak közül átnézik, hogy megfelel-e a specifikációnak és az egyes diagramtípusok összhangban vannak-e egymással. A meglévő rendszerünk helyes működését a prototípusok bemutatása előtt a tesztelési dokumentumban leírtak végrehajtása alapján ellenőrizzük és összevetjük a specifikációval, hogy az elvárt eredményt kapjuk-e. További tesztelési lehetőségek: unit tesztek írása az egyes modulokhoz vagy a kód közös átnézése (code review) egy, a vizsgált modul programozásában nem résztvevő csapattaggal. Szoftverünk minőségét a végső leadás előtt javítani kell a rendszerünkre lefuttatott kódelemzés során kapott metrikaértékek és szabálysértések figyelembevételével. Az alábbi lehetőségek vannak a szoftver megfelelő minőségének biztosítására: * Specifikáció és tervek átnézése * Teszttervek végrehajtása * Unit tesztek írása * Kód átnézése.  
&nbsp;&nbsp;&nbsp;Nagyobb részfeladatok: Adatbázis létrehozása, Felhasználói felület létrehozása, Funkcionalitás implementálása, Tesztelés, Hibák javítása.  
A projekt leadási határideje 2020.12.07.


# 4. Üzleti folyamatok modellje

 A felhasználókat a fórumba lépve azonnal a már létrehozott témák fogják várni. Ezután a felhasználónak módjában áll: 
  * Témákat olvasni
  * Regisztrálni: felhasználónév, email cím és jelszó megadásával történik
  * Bejelentkezni: felhasználónév, jelszó megadásával

 Bejelentkezés után a felhasználó képes:
  * A megnyitott témákhoz hozzászólást írni
  * Privát üzeneteket küldeni és fogadni
  * Felhasználói adatok módosítására
  * Kilépésre

 Az admin képes a fórumon:
  * Új témákat létrehozni
  * Adatok törlésére: hozzászólások, témák törlése

# 5. Követelmények

- Átlátható, letisztult dizájn
- A dizájn egyezzen a képernyőtervekkel
- Regisztrációs felület
- Felhasználói adatok védelmére vonatkozó irányelvek betartása
- A lehető legtöbb népszerű böngésző támogatása
- Belépés adminisztrátorként
- Lehetőség meglévő felhasználói adatok módosítására

# 6. Funkcionális terv
 [Funkcionális specifikációban](https://github.com/p-adrian05/SZFM_2020_10_ErrorByNight_BigProject/blob/main/functional_specification.md) megtalálható.
# 7. Fizikai környezet
Az alkalmazás ügyfelünk saját szerverén lesz tárolva és saját adatbázisához lesz kapcsolódva, ami ő általuk lesz karbantartva.

### Fejlesztési környezet
- #### Frontend:
  - Visual Studio Code
  - Google Chrome
  - Thymeleaf template engine
- #### Backend
   - IntelliJ IDEA Ultimate
   - Spring Boot keretrendszer
   - H2 adatbázis kezelő rendszer
   - Hibernate ORM/Validator
# 9. Architekturális terv
#### Architekturális tervezési minta

A rendszer tervezési mintája a MVC (Model-View-Controller) a Spring keretrendszerben.

#### Az alkalmazás rétegei, fő komponensei, ezek kapcsolatai

- A Model komponens az adatokat és a funkcionalitást csomagolja be, független a kimenet
  ábrázolásmódjától vagy az input viselkedésétől.
- A View komponensek jelenítik meg az információkat a felhasználónak.
- A Controller fogadja a bemenetet, melyet szolgáltatáskérésekké alakít a Model vagy a View felé.

<img src="./diagrams/mvc.png">

#### Változások kezelése

- Egyszerűen kezelhető változások hajthatóak végre, mivel interfészektől vagy absztrakt osztályoktól történik az objektumok függése, nem konkrét implementációktól, ezáltal az implementációk anélkül változtathatóak, hogy hatással lennének más objektumokra.

#### Rendszer bővíthetősége

- A rendszer bővítésre nyitott.
#### Biztonsági funkciók
- Felhasználók jelszavai titkosítva kerülnek tárolásra az adatbázisban.
- Jogosultság nélkül az oldalon korlátozott funkciók érhetőek el.
- HTTPS protokoll használata.

# 10. Adatbázis terv
<img src="./diagrams/db_design.png">

 [Adatmodellt legeneráló SQL szkript](./diagrams/db_script.txt)
# 11. Implementációs terv
<img src="./diagrams/implementation_plan.png" alt="UML diagram" width="1000px">

# 12. Tesztterv
- Az alkalmazás backend részének tesztelése automatizálva lesz megoldva a JUnit 5 keretrendszer segítségével. A teszthez tesztelési jegyzőkönyv készül.   
- Frontenden a felhasználói felület manuálisan lesz tesztelve, a jegyzőkönyvben leírva a tesztelt funkció részletes leírása, az elvárt helyes működés, a kapott működés és az elfogadásról szóló döntési hozatal.
- Tesztelendő modulok:
    - Adatbázis modell és DAO osztályok.
    - Service osztályok.
    - Controller osztályok.
    - Felhasználó űrlapok helyes működése bejelentkezés, regisztráció és adatok megváltoztatás esetén, hibás adat megadása esetén elvárt működés.
    - Felhasználói felület, megjelenés.


## 1. DAO réteg tesztelése

### Tesztelési környezet:
Memóriában tárolt H2 adatbázissal történik a DAO osztályok tesztelése. Create.sql fájlban található az adatbázis létrehozásához szükséges DDL utasítások. Init.sql fájlban található az adatbázis példa adattal feltöltendő DML parancsok. A tesztelés JUnit5 keretrendszerrel, test profile létrehozásával történik.

### Tesztelendő osztályok:
  - ForumDao
  - UserDao
  - RoleDao  
osztályok implementációja.

### Tesztelés célja:
A tesztelés során megbizonyosodni az osztályok függvényeinek helyes működéséről, a megfeleő sql utasítások végrehajtása és a kívánt adat a megfelelő objektumba csomagolva visszaadása.

### Összegzés:
Összesen 25 egységteszt került elkészítésre, átlagosan 80%-os metódus lefedettséggel. Minden tesztelhető függvény le lett tesztelve és a tesztek hiba nélkül lefutottak.

## 2. Service réteg tesztelése

### Tesztelési környezet:
A tesztelés JUnit5 keretrendszerrel, test profile létrehozásával történik, a Mockolást a Spring keretrendszer biztosítja, amely a DAO osztályok implementációját hivatott kiváltani.

### Tesztelendő osztályok:
  - EmailService 
  - ForumService
  - StorageService  
  - UserService
  - UtilService
osztályok implementációja.

### Tesztelés célja:
A tesztelés során megbizonyosodni az osztályok függvényeinek helyes működéséről, az üzleti logika megfelelő implementációja felől.

### Összegzés:
Összesen 28 egységteszt került elkészítésre, átlagosan 90%-os metódus lefedettséggel. Minden tesztelhető függvény le lett tesztelve és a tesztek hiba nélkül lefutottak.

## 3. Controller réteg tesztelése

### Tesztelési környezet:
A tesztelés manuálisan történt google chrome és postman segítségével.

### Tesztelendő osztályok:
  - AccountController
  - HomeController
  - PostController  
  - TopicController
  - RegistrationController

### Tesztelés célja:
A tesztelés során megbizonyosodni, hogy a Controller osztályok minden kérésre a megfelelő választ adják vissza html oldal formájában.

### Összegzés:
A klienstől érkezett HTTP kérések nagy része hibátlanul történt megválaszolásra, apróbb hibák javításra kerültek.

## 4. Felhasználói felület tesztelése

### Tesztelési környezet:
A tesztelés manuálisan történt google chrome segítségével.

### Tesztelendő osztályok:
Bejelentkezés, regisztrációs, privát üzenetek, profil, kategóriák, topikok, posztok megjelenítésére szolgáló html oldalak és a hozzá tartózó kevés javascript.

### Tesztelés célja:
A tesztelés során megbizonyosodni, hogy a html oldalak a kívánt módon néznek ki és reagálnak. 

### Összegzés:
- Bejelentkezés
  - Ha az adatbázisban megtalálható a megadott felhasználónév-jelszó páros, akkor sikeres bejelentkezés történik,  
   ellenkező esetben hibaüzenet kerül kiküldésre.
  - Sikeres bejelentkezést követően a főoldalra kerülés.
- Regisztráció:
  - Megadott felhasználónév nem kerül elfogadásra, ha az már megtalálható az adatbázisban, ekkor hibaüzenet: A felhasználónév foglalt.
  - Megadott email cím csak akkor kerül elfogadásra, ha valid a formátuma és nem található meg az adatbázisban még. ELlenkező
   esetben, hibaüzenet: Az email cím foglalt, illetve invalid email cím.
  - Jelszó esetén ha megfelel az alábbi kritériumoknak: Legalább egy kis betű szerepel, nincs szóköz, tartalmaz legalább egy 
     speciális karaktert, tartalmaz legalább egy nagy betűt és minimum 8 karakter hosszúságú. Ebben az esetben elfogadásra kerül a jelszó, ellenkező esetben hibaüzenet a megsértett kritériummal. 
  - Jelszó megerősítése a már egyszer elfogadott és megadott jeszóval. Nem egyezés esetén hibaüzenet.
- Privát üzenetek küldése
    - Egy felhasználó profilján a privát üzenet küldése linken keresztül történik. Ha az üzenet üres, nem kerül elküldés, ellenkező esetben megjelenik az üzenetek menüpontban a párbeszéd.
    - Saját magának nem küldhet a felhasználó üzenetet.
- Privát üzenetek listázása
   - Lapozható párbeszédek előre hátra működik, a gomb letiltásra kerül ha elérte a minumum vagy maximum oldalszámot.
- Felhasználói profil 
   - Megjelennek a regisztráció során az adatok, illetve az opcionáis adatok is. Privát üzenet link is megjelenik, amire rákattintva 
    üzenet küldési oldalra kerül.
- Főoldalon a forumok nevei kerülnek listázásra, és hogy hány témát tartalmaznak. Bejelentkezéskor a jobb oldalon a kedvenc topikok lista is megjelenik, ha nincs ilyen akkor üres a lista.
- Témák listázása:
  - Lapozható listában jelennek meg a témák, a gomb letiltásra kerül ha elérte a minumum vagy maximum oldalszámot.
  - Témák nevei, hány posztot tartalmaz, ki indította, mikor és mi az utolsó aktivitás időpontja jelennek meg,
  - Bejelentkezés esetén megjelenik a kedvenc topikok listája és egy új topik létrehozására egy gomb. Ha a felhasználó nincs bejelentkezve, akkor ezek nem jelennek meg a jobb oldalon. Az új topik létrehozása gombra kattintva, egy űrlap jelenik meg ahol megadható a topik neve és egy nyitó hozzászólás tartalma. Üres név és üzenet esetén nem nyitható meg az új topik. Sikeres megnyitsá után megjelenik a listában.
- Téma hozzászólások: 
  - Egy téma megnyitásakor lapozható hozzászólások jelennek meg időredni sorrendben, ahol látható melyik felhasználó kinek küldte az adott hozzászólást, mikor. Az új hosszászólás linkre kattintva megjelenik egy űrlap, ahol megadható az üzenet tartalma, üres üzenetet nem lehet küldeni, sikeres küldés után megjelenik a hosszászólások között, a lista legvégén. Válasz gombra kattintva ugyanez történik csak látható az űrlap alatt az a hozzászólás amelyikre válaszolunk. 
- Felhasználói beállítások:
  - Lehetőség van saját profilképet feltölteni, 0.5MB-nál nagyobb képet fogad el, ebben az esetben hibaüzenet kerül kiírásra. A képek formátuma jpeg,png,jpg,gif lehet, ellenkező esetben hibaüzenet kerül kiírásra.
  - További adatokra nincs megszorítás.

# 13. Telepítési terv
A telepítést ügyfelünk végzi saját webszervereire és saját adatbázisukhoz kapcsolva.
# 14. Karbantartási terv
&nbsp;&nbsp;&nbsp;Az alkalmazáshoz jelenlegi és jövőbeli problémamentes használata érdekében folyamatos
frissítést biztosítunk az átadás utáni fél évben ingyenesen.   
&nbsp;&nbsp;&nbsp;Az új verziók két hetente kerülnek kiadásra, ide tartoznak az egyszerű hibák javítása, optimalizáció, új funkciók bevezetése vagy javítása felhasználói visszajelzések alapján. Kritikus, optimális működést befolyásoló hibák esetén azonnali, mielőbbi működőképes verzió kiadása történik.
