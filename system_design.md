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
-
# 4. Üzleti folyamatok modellje
-
# 5. Követelmények
-
# 6. Funkcionális terv
-
# 7. Fizikai környezet
-
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
# 13. Telepítési terv
-
# 14. Karbantartási terv
-
