# Global Warm

Aplikacja Global Warm jest prostym programem napisanym w języku Java, który umożliwia użytkownikowi sprawdzenie informacji na temat populacji różnych gatunków zwierząt. Aplikacja korzysta z bazy danych MySQL do przechowywania danych o populacji.

## Uruchomienie aplikacji
1. Sklonuj repozytorium:
````
git clone https://github.com/juliakiczka/globalWarming.git
````
2. Skompiluj kod źródłowy:
````
javac -cp mysql-connector-java.jar:. org/example/GlobalWarm.java
````
3. Uruchom aplikację:
````
java -cp mysql-connector-java.jar:. org.example.GlobalWarm
````


## Zależności

Aplikacja korzysta z następujących zależności:

- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) - biblioteka JDBC do obsługi bazy danych MySQL.

## Konfiguracja bazy danych

1. Zainstaluj bazę danych MySQL.

2. Utwórz nową bazę danych o nazwie "global_warming_app":

```sql
CREATE DATABASE global_warming_app;
```
Użyj utworzonej bazy danych:
```sql
USE global_warming_app;
```
Utwórz tabelę "species" z odpowiednimi kolumnami:
```sql
CREATE TABLE species (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  population_2023 INT NOT NULL,
  population_2033 INT NOT NULL
);
```
Utwórz tabelę "species_data" z odpowiednimi kolumnami:
```sql
CREATE TABLE species_data (
  id INT PRIMARY KEY AUTO_INCREMENT,
  species_id INT,
  habitat VARCHAR(255),
  diet VARCHAR(255),
  status VARCHAR(255),
  FOREIGN KEY (species_id) REFERENCES species(id)
);
```
Wstaw dane do tabeli "species":
```sql
INSERT INTO species (name, population_2023, population_2033) VALUES
  ('niedźwiedzie polarne', 25000, 12500),
  ('szczurzynek koralowy', 50, 15),
  ('pantera śnieżna', 4000, 1500),
  ('suhak mongolski', 14000, 7800),
  ('słoń indyjski', 35000, 6300),
  ('pingwiny przylądkowe', 17700, 9800),
  ('kruk etiopski', 20000, 3200),
  ('żółw zielony', 9380, 400),
  ('panda wielka', 7218, 3250),
  ('orangutan borneański', 1243, 300);
```
Wstaw dane do tabeli "species_data":
```sql   
INSERT INTO species_data (species_id, habitat, diet, status) VALUES
     (1, 'Obszary polarne', 'Mięso, ryby, rośliny', 'Zagrożony'),
     (2, 'Rafy koralowe', 'Organizmy planktonowe, glony', 'Zagrożony'),
     (3, 'Górzyste obszary Azji', 'Mięso, małe ssaki', 'Krytycznie zagrożony'),
     (4, 'Stepy Mongolii', 'Rośliny, owady', 'Zagrożony'),
     (5, 'Obszary leśne', 'Rośliny, trawa', 'Zagrożony'),
     (6, 'Obszary antarktyczne', 'Ryby, kryl', 'Najmniejszej troski'),
     (7, 'Sawanny i pustynie', 'Padlina, owoce, nasiona', 'Zagrożony'),
     (8, 'Zielenie lasy i oceany', 'Rośliny wodne, małe zwierzęta', 'Zagrożony'),
     (9, 'Góry i lasy bambusowe', 'Bambus, owoce', 'Zagrożony'),
     (10, 'Las deszczowy Borneo', 'Owoce, liście, korzenie', 'Krytycznie zagrożony');
```
# Wyświetlanie danych
Po uruchomieniu aplikacji wyświetli się lista wszystkich gatunków wraz z komunikatem o wyborze konkretnego gatunku. Po wyborze gatunku aplikacja wyświetli informacje o populacji tego gatunku w roku 2023 i przewidywaną populację na rok 2033. Dodatkowo, zostanie wyświetlona średnia populacja dla wybranego gatunku w latach 2023 i 2033 oraz procentowy spadek populacji między tymi latami.

Pamiętaj, aby dostosować parametry połączenia do bazy danych (URL, nazwa użytkownika, hasło) w metodzie connectToDatabase() klasy GlobalWarm.

Dla prawidłowego działania aplikacji upewnij się, że masz zainstalowany odpowiedni sterownik JDBC (MySQL Connector/J) i dołączony do ścieżki kompilacji i uruchamiania aplikacji.

Dzięki tym krokom będziesz mógł skompilować i uruchomić aplikację Global Warm oraz korzystać z funkcjonalności związanych z bazą danych MySQL.












