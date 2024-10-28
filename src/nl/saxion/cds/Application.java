package nl.saxion.cds;

import nl.saxion.app.SaxionApp;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.models.Station;
import nl.saxion.cds.solution.Coordinate;
import nl.saxion.cds.solution.MyArrayList;
import nl.saxion.cds.solution.MyGraph;

import java.awt.*;
import java.util.Scanner;

public class Application implements Runnable {
    private static Station from;
    private static Station to;
    private static Point fromPoint;
    private static Point toPoint;
    private static final Coordinate HDR = new Coordinate(52.955276489258, 4.7611112594605);
    private static final Coordinate MT = new Coordinate(50.850276947021, 5.7055554389954);
    private static final Point HDR_POINT = new Point(600, 400);
    private static final Point MT_POINT = new Point(1024, 1840);
    private static final double LAT_FACTOR = (MT_POINT.getY() - HDR_POINT.getY()) / (HDR.latitude() - MT.latitude());
    private static final double LON_FACTOR = (MT_POINT.getX() - HDR_POINT.getX()) / (MT.longitude() - HDR.longitude());
    private static final int MAP_WIDTH = 1620;
    private static final int MAP_HEIGHT = 1920;
    private static final int MAP_FACTOR = 2;
    private static ApplicationManager applicationManager = new ApplicationManager();
    private static MyArrayList<String> route = new MyArrayList<>();
    private static SaxGraph<String> mcst;
    private static Scanner sc = new Scanner(System.in);
    private static int option = -1;

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            System.out.print("Your input: ");
            option = sc.nextInt();

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
                    SaxionApp.start(new Application(), MAP_WIDTH / MAP_FACTOR, MAP_HEIGHT / MAP_FACTOR);
                    break;
                case 5:
                    showMCST();
                    SaxionApp.start(new Application(), MAP_WIDTH / MAP_FACTOR, MAP_HEIGHT / MAP_FACTOR);
                    break;
                case 6:
                    SaxionApp.start(new Application(), MAP_WIDTH / MAP_FACTOR, MAP_HEIGHT / MAP_FACTOR);
                    break;
                default:
                    System.out.println("Invalid selection, try again!");
            }
        }
    }

    @Override
    public void run() {
        SaxionApp.drawImage("resources/Nederland.png", 0, 0, MAP_WIDTH / MAP_FACTOR, MAP_HEIGHT / MAP_FACTOR);

        if (option == 4) {
            displayShortestRoute();
        } else if (option == 5) {
            displayMCST();
        } else if (option == 6) {
            displayRailNetwork();
        }
    }

    private static void displayMenu() {
        System.out.println("WELCOME TO MANAGER TRACK APPLICATION");
        System.out.println("Select one option:");
        System.out.println("1. Show a station information by its code");
        System.out.println("2. Show a station information by its name");
        System.out.println("3. List stations by type");
        System.out.println("4. Show the shortest route between 2 stations:");
        System.out.println("5. Show the minimum number of rail connections");
        System.out.println("6. Show rail network");
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
        from = applicationManager.findStationByCode(stationCode1);
        System.out.print("Second station code: ");
        String stationCode2 = sc.next();
        to = applicationManager.findStationByCode(stationCode2);

        fromPoint = convertCoordinate(from.getCoordinate());
        toPoint = convertCoordinate(to.getCoordinate());

        route = applicationManager.determineShortestRoute(stationCode1, stationCode2);
    }

    private static void showMCST() {
        mcst = applicationManager.showMCST();
    }

    private static void displayRailNetwork() {
        MyGraph<String> connections = applicationManager.getGraph();

        for (String connection : connections) {
            Station station = applicationManager.findStationByCode(connection);
            Point stationPoint = convertCoordinate(station.getCoordinate());
            drawStation(stationPoint, Color.GREEN, 5);

            for (SaxGraph.DirectedEdge<String> edge : connections.getEdges(connection)) {
                String code = edge.to();
                Station toStation = applicationManager.findStationByCode(code);
                Point toStationPoint = convertCoordinate(toStation.getCoordinate());

                SaxionApp.setBorderColor(Color.ORANGE);
                SaxionApp.drawLine(stationPoint.x, stationPoint.y, toStationPoint.x, toStationPoint.y);
                SaxionApp.setBorderSize(1);
            }
        }
    }

    private static Point convertCoordinate(Coordinate coordinate) {
        double lon = coordinate.longitude() - HDR.longitude();
        double lat = HDR.latitude() - coordinate.latitude();

        return new Point((int) ((lon * LON_FACTOR) + HDR_POINT.getX()) / MAP_FACTOR,
                (int) (lat * LAT_FACTOR + HDR_POINT.getY()) / MAP_FACTOR);
    }

    public void displayShortestRoute() {
        if (!route.isEmpty()) {
            Point previousPoint = fromPoint;

            for (int i = 0; i < route.size(); i++) {
                String stationCode = route.get(i);
                Station station = applicationManager.findStationByCode(stationCode);
                Point stationPoint = convertCoordinate(station.getCoordinate());
                drawStation(stationPoint, Color.GREEN, 5);

                if (i > 0) {
                    SaxionApp.setBorderColor(Color.ORANGE);
                    SaxionApp.drawLine(previousPoint.x, previousPoint.y, stationPoint.x, stationPoint.y);
                    SaxionApp.setBorderSize(1);
                }

                previousPoint = stationPoint;
            }
        }

        drawStationLabel(fromPoint, from.getName(), Color.WHITE);
        drawStationLabel(toPoint, to.getName(), Color.WHITE);
    }

    public void displayMCST() {
        if (!mcst.isEmpty()) {
            for (String connection : mcst) {
                Station station = applicationManager.findStationByCode(connection);
                Point stationPoint = convertCoordinate(station.getCoordinate());
                drawStation(stationPoint, Color.GREEN, 5);

                for (SaxGraph.DirectedEdge<String> edge : mcst.getEdges(connection)) {
                    String code = edge.to();
                    Station toStation = applicationManager.findStationByCode(code);
                    Point toStationPoint = convertCoordinate(toStation.getCoordinate());

                    SaxionApp.setBorderColor(Color.ORANGE);
                    SaxionApp.drawLine(stationPoint.x, stationPoint.y, toStationPoint.x, toStationPoint.y);
                    SaxionApp.setBorderSize(1);
                }
            }
        }
    }

    private void drawStationLabel(Point point, String label, Color color) {
        SaxionApp.setFill(color);
        SaxionApp.setBorderColor(color);

        int labelX = (int) point.getX() + 15;
        int labelY = (int) point.getY() - 5;

        SaxionApp.drawText(label, labelX, labelY, 13);
    }

    private static void drawStation(Point point, Color color, int radius) {
        SaxionApp.setBorderColor(Color.GREEN);
        SaxionApp.setBorderSize(1);
        SaxionApp.setFill(color);
        SaxionApp.drawCircle((int) point.getX(), (int) point.getY(), radius);
    }
}