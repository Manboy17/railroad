package nl.saxion.cds;

import nl.saxion.cds.models.Station;
import nl.saxion.cds.solution.MyArrayList;

import java.util.Scanner;

public class Application {
    private static ApplicationManager applicationManager = new ApplicationManager();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            System.out.print("Your input: ");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    searchStationByCode();
                    break;
                case 2:
                    searchStationsByName();
                    break;
                case 3:
                    searchStationsByType();
                    break;
                case 4:
                    showShortestRoute();
                    break;
                default:
                    System.out.println("Invalid selection, try again!");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("WELCOME TO MANAGER TRACK APPLICATION");
        System.out.println("Select one option:");
        System.out.println("1. Show a station information by its code");
        System.out.println("2. Show a station information by its name");
        System.out.println("3. List stations by type");
        System.out.println("4. Show the shortest route between 2 stations:");
    }

    private static void searchStationByCode() {
        Station station = null;

        while (station == null) {
            System.out.print("Station code: ");
            String stationCode = sc.next();

            station = applicationManager.findStationByCode(stationCode);

            if (station == null) {
                System.out.println("No information found!");
            }
        }

        System.out.println("Station information:");
        System.out.println("-------------------------");
        System.out.println("Name: " + station.getName());
        System.out.println("Country: " + station.getCountry());
        System.out.println("Type: " + station.getType());
        System.out.println("-------------------------");
    }


    private static void searchStationsByName() {
        System.out.print("Station name: ");
        String name = sc.next();
        MyArrayList<Station> stations = applicationManager.findStationsByName(name);
        if (stations.size() > 1) {
            System.out.println("Stations found:");
            System.out.println("-------------------------");
            for (int i = 0; i < stations.size(); i++) {
                Station station = stations.get(i);
                System.out.println((i + 1) + ": " + station.getName() + " (Code: " + station.getCode() + ")");
            }

            System.out.print("Select a station number you want to view: ");
            int option = sc.nextInt();

            if (option > 0 && option <= stations.size()) {
                Station selectedStation = stations.get(option - 1);
                System.out.println("-------------------------");
                System.out.println("Name: " + selectedStation.getName());
                System.out.println("Country: " + selectedStation.getCountry());
                System.out.println("Type: " + selectedStation.getType());
                System.out.println("-------------------------");
            }
        } else if (stations.size() == 1) {
            System.out.println("Station found:");
            Station station = stations.get(0);
            System.out.println("-------------------------");
            System.out.println("Name: " + station.getName());
            System.out.println("Country: " + station.getCountry());
            System.out.println("Type: " + station.getType());
            System.out.println("-------------------------");
        } else {
            System.out.println("-------------------------");
            System.out.println("No stations were found for [ " + name + " ]");
            System.out.println("-------------------------");
        }
    }

    private static void searchStationsByType() {
        System.out.print("Station type: ");
        String type = sc.next();
        MyArrayList<Station> stations = applicationManager.findStationsByType(type);
        if (!stations.isEmpty()) {
            System.out.println("-------------------------");
            for (Station station : stations) {
                System.out.println(station.getName());
            }
            System.out.println("-------------------------");
        } else {
            System.out.println("-------------------------");
            System.out.println("No stations were found by " + type + " type");
            System.out.println("-------------------------");
        }
    }

    private static void showShortestRoute() {
        System.out.print("First station code: ");
        String stationCode1 = sc.next();
        System.out.print("Second station code: ");
        String stationCode2 = sc.next();
        applicationManager.determineShortestRoute(stationCode1, stationCode2);
    }
}
