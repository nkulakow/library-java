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
5. Klasa LibraryContext - Klasa hermetyzująca logikę aplikacji wykorzystująca obiekty klas Admin, CommonUser i Book. Zawiera metody modyfikujące stan lokalnego repozytorium biblioteki. Wykorzystuje metody klasy database do aktualizacji lokalnego repozytorium i wprowadzania zmian do bazy danych.
6. Klasa Admin - klasa reprezentująca administratora biblioteki. Administratorzy mogą modyfikować kolekcje z których składa się baza danych.
Mogą także wyszukiwać obiekty w bazie danych.
7. Klasa Book - klasa reprezentująca książkę. Ma atrybuty odpowiadające książkom wypożyczanym ze standardowej biblioteki oraz odpowiadające im odpowiednie gettery i settery. Jest to klasa odpowiadająca danym.
8. Klasa CommonUser - klasa reprezentująca użytkowników biblioteki. Mogą wypożyczać książki, zwracać je, wyświetlać wypożyczone książki i sprawdzać, czy nie przetrzymują wypożyczonych książek zbyt długo.
9. Klasa User - klasa abstrakcyjna, zawiera atrybuty i metody charakteryzujące klasy CommonUser i Admin. 
10. Interfejs Isearch - interfejs z jedną metodą służącą do ustawienia środowiska wyszukiwania obiektu. Intefejs zapewnia polimorfizm i modyfikowalność środowiska wyszukiwania w zależności od wyszukiwanego obiektu.
11. Interfejs LibraryContextActions - interfejs, którego metody muszą być implementowane przez obiekty biblioteczne. Umozliwia wykorzystanie polimorfizmu do uproszczenia wprowadzania zmian w bazie. Obiekty w bazie mogą być identyfikowane na podstawie operacji jakie można na nich wykonywać a nie na podstawie ich typu.
12. Typ wyliczeniowy LibContextOptions - definiuje typy operacji jakie można wykonać na kolekcjach obiektów biblioteki w celu ich modyfikacji.
13. Klasa Book - reprezentuje książkę, ma atrybuty takie jak nazwa, kategoria, data zwrotu, etc., gettery, settery.
14. Klasa User - abstrakcyjna klasa reprezentująca użytkownika, rozszerzana przez klasy CommonUser i Admin
15. Klasa CommonUser - klasa reprezentująca zwykłego użytkownika, ma metody reprezentujące czynności: wypożyczanie, oddawanie, przedłużanie, opłacanie kar za nieoddane książki, ma atrybuty takie jak imię, nazwisko, id, liczba wypożyczonych książek, etc.
. Klasa Admin - ma uprawnienia zwykłego użytkownika, ale dodatkowo rozszerza jego możliwości dzięki zastosowaniu dziedziczenia. Może wykonywać czynności administracyjne na systemie zarządzania biblioteką.
16. Klasa LibraryDatabase - klasa reprezentująca bazę danych.
17. Klasy reprezentujące pewne wyjątki m.in. InvalidIdException, NullOrEmptyStringException

**Testy:**  
Do implementacji testów jednostkowych planujemy używać JUnit. Poszczególne metody i funkcjonalności będą sprawdzane oddzielnie.

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
- Maven
- Sqldeveloper / baza Oracle
