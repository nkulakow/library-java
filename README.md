# PAP22L-Z01

### Członkowie zespołu:
- Marcin Wawrzyniak 318741
- Mateusz Kiełbus 318668
- Janek Hapunik 318659
- Nel Kułakowska 318687

## Aktualności
<ins>Zrealizowane do te pory:</ins>
- Prorotypy klas zarządzających aplikacją (LibraryContext, LibraryDatabase itp.)
- Przestawienie się na Oracle
- Dostęp do bazy danych i podstawowe operacje
- Prototypowe logowanie (dla administratora)
- Naprawiona ikonka
- Poprawienie GUI
- Wprowadzenie w GUI komunikatów do informowania użytkownika o wyjątkowych sytuacjach (np. błędach w połączeniu z bazą danych)
- Przeniesienie danych na Oracle
- Rozbudowanie i usprawnienie operacji na bazie danych
- Funkcjonalności administratora (logowanie, dodawanie/modyfikacja/usuwanie użytkowników/książek, przeszukiwanie bazy książek i użytkowników)
- Funkcjonalności użytkownika (logowanie, zamawianie/wypożyczanie książek, zmiana własnych danych, przeszukiwanie bazy książek)
- Testy
- Refaktoryzacja
- Logi
- Mockowanie


<ins>Co się nie udało?:</ins>
- Stworzenie "mail server"
- próba implementacji JPA Hibernate (branch oracle_cd). Problemem okazała się trudność w zastosowaniu JPA w oracle database. W rozwiązaniu tego i bardziej czytelnym kodzie klasy LibraryDatabase pomogłoby zastosowanie Spring Boot.
- Skorzystanie z plików CSS - dużo pracy, za późno wpadliśmy na ten pomysł


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
1. Przypomnienia o zbliżaniu się terminu zwrotu - nie udało się
2. Możliwość automatycznego wysyłania maili - nie udało się
3. Utworzenie kont dla klientów (login i hasło, wypożyczenia, zamówienia, naliczone opłaty, zbliżające się terminy zwrotu, oceny książek itd.) - udało się

**Implementacja interfejsu graficznego:**  
Jeśli chodzi o implementację interfejsu graficznego, to zmiany do GUI wprowadzaliśmy ręcznie, pisząc kod klas zarządzających interfejsem graficznym. 

**Klasy:**
1. GUI
    - Klasa LibraryGUI - klasa zawierająca i zarządzające głownymi elementami GUI - stroną logowania i główną.
    - Klasa LogInPage - strona logowania dla wszystkich użytkowników.
    - Klasa MainPage - strona główna dla wszystkich użytkowników, zawiera najważniejsze kontrolki. Jej głownym elementem jest pole content_panel, którego zawartość jest wizualizacją logiki aplikacji. MainPage to także główny ActionListener aplikacji.
    - Klasa ComponentDesigner - jest to fabryka odpowiednich komponentów page'ów.
    - Klasa FrameContentManager i wszystkie klasy pochodne - klasy zarządzające całą logiką GUI. Każda ta klasa ma zdefiniowaną własną metodę manage(JPanel content_panel), której zadaniem jest dostosowanie zawartości wyświetlanej na ekranie, do aktualnego stanu aplikacji, a także wymiana danych między GUI, a logiką (LibraryContext). Szkielet działania: 1.Pobierz dane od użytkownika. 2. Prześlij dane do logiki i odbierz rezultat. 3. Dostosuj do rezultatu zawartość ekranu.
    - Klasa ObjectTable - używana do zarządzania tabelkami.
    - Klasa Prompt - klasa zarządzająca promptem, wyświetlanym po wprowadzeniu zmian w BD.

2. LibraryContext
    - Klasa LibraryContext - klasa hermetyzująca logikę aplikacji wykorzystująca obiekty klas Admin, CommonUser i Book. Zawiera metody modyfikujące stan lokalnego repozytorium biblioteki. Wykorzystuje metody klasy database do aktualizacji lokalnego repozytorium i wprowadzania zmian do bazy danych.
    - Interfejs Isearch - interfejs z jedną metodą służącą do ustawienia środowiska wyszukiwania obiektu. Intefejs zapewnia polimorfizm i modyfikowalność środowiska wyszukiwania w zależności od wyszukiwanego obiektu.
    - Interfejs LibraryContextActions - interfejs, którego metody muszą być implementowane przez obiekty biblioteczne. Umozliwia wykorzystanie polimorfizmu do uproszczenia wprowadzania zmian w bazie. Obiekty w bazie mogą być identyfikowane na podstawie operacji jakie można na nich wykonywać a nie na podstawie ich typu.
    - Typ wyliczeniowy LibContextOptions - definiuje typy operacji jakie można wykonać na kolekcjach obiektów biblioteki w celu ich modyfikacji.
    - Klasa Book - reprezentuje książkę. Ma atrybuty odpowiadające książkom wypożyczanym ze standardowej biblioteki oraz odpowiadające im odpowiednie gettery i settery. Jest to klasa odpowiadająca danym.
    - Klasa User - abstrakcyjna klasa reprezentująca użytkownika, rozszerzana przez klasy CommonUser i Admin
    - Klasa CommonUser - klasa reprezentująca użytkowników biblioteki. Mogą wypożyczać książki, zwracać je, wyświetlać wypożyczone książki i sprawdzać, czy nie przetrzymują wypożyczonych książek zbyt długo i czy nie naliczone zostały opłaty.
    - Klasa Admin - klasa reprezentująca administratorów biblioteki. Może wykonywać czynności administracyjne na systemie zarządzania biblioteką. Może modyfikować kolekcje z których składa się baza danych. Może także wyszukiwać obiekty w bazie danych.
    - Klasy reprezentujące pewne wyjątki m.in. InvalidIdException, NullOrEmptyStringException

3. Database
    - Klasa LibraryDatabase - klasa reprezentująca bazę danych i realizująca opercje na niej (zapytania, modyfikacje, dodawanie, usuwanie). Posiada także metody do odwzorowania danych w tabeli w ich Javową reprezentację.
    - Klasa DatabaseConstants - znajdują się w niej stałe z których korzysta klasa LibraryDatabase.

**Testy:**  
Do implementacji testów jednostkowych używamy JUnit.
Przygotowane testy jednostkowe weryfikują działanie metod m.in. z klasy LibraryContext odpowiedzialnych za wdrożenie logiki aplikacji. Sprawdzone zostało działanie metod odpowiedzialnych za wyszukiwanie obiektów, ich dodawanie i usuwanie, a także modyfikację kolekcji bibliotecznych. 
Testy testujące klasę LibraryContext mockują statyczne metody klasy LibraryDatabase, aby nie zależały od wartości BD ani jej nie zmieniały. Testy, poza wyżej wymienionymi metodami, można uruchomić za pomocą komendy *mvn test*.

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
    - org.apache.logging.log4j - wykorzystywania do tworzenia logów
    - java.util - podstawowe narzędzie do przechowywania danych lokalnie w kolekcjach oraz do dekodowania haseł adminów
    - lombok - wykorzystywana do automatycznego tworzenia getterów i setterów
    - java.time - wykorzystywana do reprezentowania danych typu czas/data
    - org.mockito - wykorzystywana do mockowania
- JDBC
- Maven
- Sqldeveloper / baza Oracle
