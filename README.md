# PAP22L-Z01

### Członkowie zespołu:
- Marcin Wawrzyniak 318741
- Mateusz Kiełbus 318668
- Janek Hapunik 318659
- Nel Kułakowska 318687

## Aktualności
<ins>Zrealizowane do te pory:</ins>
- Prorotypy klas zarządzających aplikacją (LibraryContext, LibraryDatabase itp.)
- Wstępne GUI
- Przestawienie się na Oracle
- Dostęp do bazy danych i podstawowe operacje
- Prototypowe logowanie (dla administratora)
- Naprawiona ikonka

<ins>Do zrobienia:</ins>
- Zrefaktoryzowanie kodu
- Rozbudowanie i usprawnienie operacji na bazie danych poprzez utworzenie klasy Database
- Zrobienie ładniejszego GUI (temat przewodni: Kubuś Puchatek)
- Stworzenie "mail server" (spróbować)


## Instrukcja instalacji i obsługi
<ins>Wymagania systemowe</ins>
- Wersja Javy: 19
- Wersja Mavena: 3.8.1+ (najlepiej 3.8.6) 

<ins>Uruchamianie z linii komend w systemie Linux</ins>
- utworzenie pliku jar: *mvn install* lub *mvn package* lub *mvn -Dmaven.test.skip=true install* (jeśli chcemy pominąć testy)
- uruchomienie pliku jar: *java -jar target/pap22l-z01-1.0-SNAPSHOT.jar*

## Treść zadania:
Chcemy stworzyć aplikację **desktopową** pozwalającą na **zarządzanie biblioteką** z użyciem języka **Java**.
  

**Wymagania:**
1. Dostęp do bazy książek i zajerestrowanych klientów (w tym wyszukiwanie, wyświetlanie jako lista)
2. Możliwość edycji baz książek i klientów (nowe książki/nowi klienci/zmiana danych elementu)
3. Możliwość edycji stanu książek (wypożyczenia, zamówienia)
4. Możliwość naliczenia opłat (za przetrzymanie książek itd.)
5. Konto administratora (login, hasło)

**Dodatkowe funkcjonalności:**
1. Przypomnienia o zbliżaniu się terminu zwrotu
2. Możliwość automatycznego wysyłania maili
3. Utworzenie kont dla klientów (login i hasło, wypożyczenia, zamówienia, naliczone opłaty, zbliżające się terminy zwrotu, oceny książek itd.)

**Implementacja interfejsu graficznego:**  
Do implementacji interfejsu graficznego wykorzystamy narzędzia środowiska IntelliJ IDEA pozwalająca na zautomatyzowanie projektowania interfejsu graficznego(np. mechanizm pozwalający wygenerować formularze).
Przewidujemy także wprowadzanie zmian do GUI ręcznie, pisząc kod klas zarządzających interfejsem graficznym. 

**Klasy:**
1. Klasa LibraryGUI - klasa zawierająca definicje elementów graficznych, obiekty klas kontrolek i stron, zarządzająca nimi, aktualizująca je.
2. Klasy utworzone do zarządzania kontrolkami.
3. Klasa MainPage - klasa przedstawiająca elementy graficzne strony głównej, obok ma zdefiniowane klasy kontrolek: OptionPanel i BottomPanel.
4. Klasa LoginPage - klasa przedstawiająca elementy graficzne strony logowania.
4. Klasa LibraryContext - klasa sterująca. Zawiera obiekty klas Admin, User na zasadzie kompozycji. Implementuje metody, które służą do komunikacji między obiektami zawartymi w jej atrybutach. Steruje całą aplikacją biblioteki łącznie z korzystaniem z bazy danych. 
5. Klasa Book - reprezentuje książkę, ma atrybuty takie jak nazwa, kategoria, data zwrotu, etc., gettery, settery.
6. Klasa User - abstrakcyjna klasa reprezentująca użytkownika, rozszerzana przez klasy CommonUser i Admin
7. Klasa CommonUser - klasa reprezentująca zwykłego użytkownika, ma metody reprezentujące czynności: wypożyczanie, oddawanie, przedłużanie, opłacanie kar za nieoddane książki, ma atrybuty takie jak imię, nazwisko, id, liczba wypożyczonych książek, etc.
8. Klasa Admin - ma uprawnienia zwykłego użytkownika, ale dodatkowo rozszerza jego możliwości dzięki zastosowaniu dziedziczenia. Może wykonywać czynności administracyjne na systemie zarządzania biblioteką.
9. Klasa LibraryDatabase - klasa reprezentująca bazę danych.
10. Klasy reprezentujące pewne wyjątki m.in. InvalidIdException, NullOrEmptyStringException

**Testy:**  
Do implementacji testów jednostkowych planujemy używać JUnit. Poszczególne metody i funkcjonalności będą sprawdzane oddzielnie.

**Logowanie:**  
Klasy zarządzające częścią logiczną, tzn.: LibraryContext, User, LibraryDatabase, a także klasy testujące posiadają obiekty klasy Logger. Za ich pomocą odpowiednich plików wysyłane są logi informujące o wykonaniu bardziej skomplikowanych zadań i o złapanych wyjątkach
w danej metodzie wraz z wiadomością co dokładnie poszło nie tak. Wykorzystanie RolingFiles pozwala na porównanie zapisanych logów z otrzymanymi przy poprzednim uruchomieniu. Pliki zapisujące logi znajdują się w folderze target/logs:
- all_logs.log i all_logs_prev.log - logi z klas tworzących projekt oraz logi zapisane w poprzednim uruchomieniu w all_logs.log
- test.log i test_prev.log - logi z wszystkich klas testujących oraz poprzednie logi z test.log
- test_log.log i test_log_prev.log - logi wyłącznie z klasy LogTest i poprzednie logi z test_log.log

### Wybrane technologie:
- Biblioteki Javy:
    - javax.swing - główne narzędzie do tworzenia okienka aplikacji
    - java.sql - wykorzystywana do operacji na bazach danych
    - java.awt - bilbioteka graficzna, używana obok biblioteki swing, m.in. do obsługi zdarzeń (np. zalogowanie) i kolorów 
    - java.io - wykorzystywania do odczytywania treści plików np. info.txt zawierający dane logowania do bazy danych
    - javax.mail - wykorzystywana do automatycznego wysyłania maili 
    - org.apache.logging.log4j - wykorzystywania do tworzenia logów
    - java.util - podstawowe narzędzie do przechowywania danych lokalnie w kolekcjach oraz do dekodowania haseł adminów
    - lombok - wykorzystywana do automatycznego tworzenia getterów i setterów
    - java.time - wykorzystywana do reprezentowania danych typu czas/data
- JDBC
- Maven
- Sqldeveloper / baza Oracle
