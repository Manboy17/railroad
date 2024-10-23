package nl.saxion.cds;

import nl.saxion.app.SaxionApp;
import nl.saxion.cds.collection.SaxGraph;
import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.models.Station;
import nl.saxion.cds.models.StationReader;
import nl.saxion.cds.models.Track;
import nl.saxion.cds.models.TrackReader;
import nl.saxion.cds.solution.Coordinate;
import nl.saxion.cds.solution.MyArrayList;
import nl.saxion.cds.solution.MyGraph;
import nl.saxion.cds.solution.MyHashMap;

import java.util.Comparator;

public class ApplicationManager implements Runnable {
    private static final int MAP_WIDTH = 1620;
    private static final int MAP_HEIGHT = 1920;
    private static final int MAP_FACTOR = 2;
    MyArrayList<Station> stations;
    MyArrayList<Track> tracks;
    private MyHashMap<String, Station> stationMap;
    private MyGraph<String> graph;

    public static void main(String[] args) {
        SaxionApp.start(new ApplicationManager(), MAP_WIDTH / MAP_FACTOR, MAP_HEIGHT / MAP_FACTOR);
        SaxionApp.drawImage("resources/Nederland.png", 0, 0, 810, 960);
    }

    public ApplicationManager() {
        run();
    }

    @Override
    public void run() {
        var stationReader = new StationReader("resources/stations.csv");
        var trackReader = new TrackReader("resources/tracks.csv");
        stations = stationReader.readStationsFromCSVFile();
        tracks = trackReader.readTracksFromCSVFile();

        stationMap = new MyHashMap<>(stations.size());

        for (Station station : stations) {
            String code = station.getCode();
            stationMap.add(code, station);
        }

        graph = new MyGraph<>(stations.size());
        initializeGraph();
    }

    private void initializeGraph() {
        for (Track track : tracks) {
            String fromCode = track.getFrom();
            String toCode = track.getTo();
            double distance = track.getDistance();

            graph.addEdgeBidirectional(fromCode, toCode, distance);
        }
    }

    public MyArrayList<Station> getStations() {
        return stations;
    }

    public MyArrayList<Track> getTracks() {
        return tracks;
    }

    public Station findStationByCode(String code) {
        return stationMap.get(code);
    }

    public MyArrayList<Station> findStationsByName(String name) {
        MyArrayList<Station> matchedStations = new MyArrayList<>();

        for (Station station : stations) {
            if (station.getName().startsWith(name)) {
                matchedStations.addLast(station);
            }
        }

        return matchedStations;
    }

    public MyArrayList<Station> findStationsByType(String type) {
        MyArrayList<Station> foundStations = new MyArrayList<>();

        for (Station station : stations) {
            if (station.getType().equals(type)) {
                foundStations.addLast(station);
            }
        }

        foundStations.simpleSort(Comparator.comparing(Station::getName));

        return foundStations;
    }

    public void determineShortestRoute(String stationCode1, String stationCode2) {

        SaxGraph.Estimator<String> estimator = (fromStationCode, toStationCode) -> {
            Station from = findStationByCode(stationCode1);
            Station to = findStationByCode(stationCode2);

            if (from == null || to == null) {
                return -1;
            }

            Coordinate fromCoordinate = new Coordinate(from.getCoordinate().latitude(), from.getCoordinate().longitude());
            Coordinate toCoordinate = new Coordinate(to.getCoordinate().latitude(), to.getCoordinate().longitude());

            return Coordinate.haversineDistance(fromCoordinate, toCoordinate);
        };

        SaxList<SaxGraph.DirectedEdge<String>> edges = graph.shortestPathAStar(stationCode1, stationCode2, estimator);

        if (!edges.isEmpty()) {
            double totalDistance = 0;
            System.out.println("Shortest path from " + stationCode1 + " to " + stationCode2 + ":");

            for (SaxGraph.DirectedEdge<String> edge : edges) {
                System.out.println(edge.from() + " -> " + edge.to() + " (distance: " + edge.weight() + ")");
                totalDistance += edge.weight();
            }

            System.out.println("Total distance: " + totalDistance);
        } else {
            System.out.println("No route found, try again!");
        }
    }
}
