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
- Prototypowa baza danych
- Dostęp do bazy danych i podstawowe operacje
- Prototypowe logowanie (dla administratora)

<ins>Do zrobienia:</ins>
- Naprawienie niedziałającej ikonki :(
- Zrefaktoryzowanie kodu
- Rozbudowanie i usprawnienie operacji na bazie danych
- Zrobienie ładniejszego GUI (temat przewodni: Kubuś Puchatek)
- Przestawienie się z obecnej bazy danych na Oracle


## Instrukcja instalacji i obsługi
<ins>Wymagania systemowe</ins>
- Wersja Javy: 19
- Wersja Mavena: 3.8.1+ (najlepiej 3.8.6) 

<ins>Uruchamianie z linii komend w systemie Linux</ins>
- utworzenie pliku jar: *mvn install* lub *mvn package*
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
1. GUI
    - Klasa LibraryGUI - klasa zawierająca głowne elementy GUI - strony logowania, użytkownika i administratora.
    - Klasa LogInPage - strona logowania dla wszystkich użytkowników.
    - Klasa MainPage - strona administratora, zawiera najważniejsze kontrolki. Jej głownym elementem jest pole content_panel, którego zawartość jest wizualizacją logiki aplikacji. Jest to także główny ActionListener aplikacji.
    - Klasa UserPage - odpowiednik MainPage dla użytkownika nie-administratora.
    - Klasy AddingPanel, BookAddingPanel, BottomPanel, OptionPanel - dostosowane do GUI aplikacji JPanele. Ich głównym zadaniem jest przechowywanie zawartości wyświetlanej na ekranie.
    - Klasa FrameContentManager i wszystkie klasy pochodne - klasy zarządzające całą logiką GUI. Każda ta klasa ma zdefiniowaną własną metodę manage(JPanel content_panel), której zadaniem jest dostosowanie zawartości wyświetlanej na ekranie, do aktualnego stanu aplikacji.
    - Pozostałe klasy (InfoListFont, etc.) - klasy służące do zebrania pewnych własności GUI, takich jak kolor, rozmiar czcionki itd.
4. Klasa LibraryContext - klasa sterująca. Zawiera obiekty klas Admin, User na zasadzie kompozycji. Implementuje metody, które służą do komunikacji między obiektami zawartymi w jej atrybutach. Steruje całą aplikacją biblioteki łącznie z korzystaniem z bazy danych. 
5. Klasa Book - reprezentuje książkę, ma atrybuty takie jak nazwa, kategoria, data zwrotu, etc., gettery, settery.
6. Klasa User - klasa reprezentująca użytkownika, ma metody reprezentujące czynności: wypożyczanie, oddawanie, przedłużanie, opłacanie kar za nieoddane książki, ma atrybuty takie jak imię, nazwisko, id, liczba wypożyczonych książek, etc.
7. Klasa Admin - ma uprawnienia zwykłego użytkownika, ale dodatkowo rozszerza jego możliwości dzięki zastosowaniu dziedziczenia. Może wykonywać czynności administracyjne na systemie zarządzania biblioteką.
8. Klasa LibraryDatabase - klasa reprezentująca bazę danych.
9. Klasy reprezentujące pewne wyjątki m.in. InvalidIdException, NullOrEmptyStringException

**Testy:**  
Do implementacji testów jednostkowych planujemy używać JUnit. Poszczególne metody i funkcjonalności będą sprawdzane oddzielnie.

### Wybrane technologie:
- Biblioteki Javy:
    - javax.swing - główne narzędzie do tworzenia okienka aplikacji
    - java.sql - wykorzystywana do operacji na bazach danych
    - java.awt - bilbioteka graficzna, używana obok biblioteki swing, m.in. do obsługi zdarzeń (np. zalogowanie) i kolorów 
    - javax.mail - wykorzystywana do automatycznego wysyłania maili 
- Maven
- Sqldeveloper
