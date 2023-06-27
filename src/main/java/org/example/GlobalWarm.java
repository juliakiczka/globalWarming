package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GlobalWarmingApp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/global_warming_app";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Julia160703!";
    private static Map<String, PopulationData> populations;

    public static void main(String[] args) {
        initializePopulationsFromDatabase();
        displayOptions();
        String selectedSpecies = getUserInput();
        showPopulation(selectedSpecies);
    }

    private static void initializePopulationsFromDatabase() {
        populations = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT name, population_2023, population_2033 FROM species";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String speciesName = resultSet.getString("name");
                int population2023 = resultSet.getInt("population_2023");
                int population2033 = resultSet.getInt("population_2033");
                PopulationData populationData = new PopulationData(population2023, population2033);
                populations.put(speciesName, populationData);
            }
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
            System.out.println("Liczba pozostałych osobników [" + species + "] w 2023 roku: " + populationData.getPopulation2023());
            System.out.println("Liczba pozostałych osobników [" + species + "] w 2033 roku: " + populationData.getPopulation2033());
        } else {
            System.out.println("Brak danych na temat wybranego gatunku.");
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