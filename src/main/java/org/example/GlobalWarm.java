package org.example;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GlobalWarm {
    private static Connection connection;
    private static Map<String, PopulationData> populations;

    public static void main(String[] args) {
        connectToDatabase();
        initializePopulations();
        displayOptions();
        String selectedSpecies = getUserInput();
        showAveragePopulation(selectedSpecies);
        showPopulation(selectedSpecies);
        closeConnection();
    }

    private static void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/global_warming_app";
        String username = "root";
        String password = "Julia160703!";

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void initializePopulations() {
        populations = new HashMap<>();

        // Pobierz dane populacji z bazy danych
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name, population_2023, population_2033 FROM species");

            while (resultSet.next()) {
                String species = resultSet.getString("species");
                int population2023 = resultSet.getInt("population_2023");
                int population2033 = resultSet.getInt("population_2033");
                populations.put(species, new PopulationData(population2023, population2033));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayOptions() {
        System.out.println("Dostępne gatunki:");
        for (String species : populations.keySet()) {
            System.out.println("- " + species);
        }
    }

    private static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wybierz gatunek zwierzęcia: ");
        return scanner.nextLine().toLowerCase();
    }

    private static void showPopulation(String species) {
        if (populations.containsKey(species)) {
            PopulationData populationData = populations.get(species);
            System.out.println("Liczba pozostałych osobników [" + species + "] w 2023 roku: " + populationData.getPopulation2023() + "\n");
            System.out.println("Liczba pozostałych osobników [" + species + "] w 2033 roku: " + populationData.getPopulation2033() + "\n");

            double populationGrowthRate = ((double) (populationData.getPopulation2033() - populationData.getPopulation2023()) / populationData.getPopulation2023()) * 100;
            double populationDecreaseRate = Math.abs(populationGrowthRate); // Obliczenie wartości bezwzględnej wzrostu
            System.out.println("Procentowy spadek populacji [" + species + "] między rokiem 2023 a 2033: " + populationDecreaseRate + "%" + "\n");
        } else {
            System.out.println("Brak danych na temat wybranego gatunku.");
        }
    }

    private static void showAveragePopulation(String species) {
        if (populations.containsKey(species)) {
            PopulationData populationData = populations.get(species);
            int population2023 = populationData.getPopulation2023();
            int population2033 = populationData.getPopulation2033();
            double averagePopulation = (population2023 + population2033) / 2.0;
            System.out.println("\nŚrednia populacja [" +
                    species + "] w latach 2023 i 2033: " + averagePopulation + "\n");
        } else {
            System.out.println("Brak danych na temat wybranego gatunku.");
        }
    }

    private static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class PopulationData {
        private int population2023;
        private int population2033;

        public PopulationData(int population2023, int population2033) {
            this.population2023 = population2023;
            this.population2033 = population2033;
        }

        public int getPopulation2023() {
            return population2023;
        }

        public int getPopulation2033() {
            return population2033;
        }
    }
}