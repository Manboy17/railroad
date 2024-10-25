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

import java.awt.*;
import java.util.Comparator;

public class ApplicationManager {
    private MyArrayList<Station> stations;
    private MyArrayList<Track> tracks;
    private MyHashMap<String, Station> stationMap;
    private MyGraph<String> graph;

    public ApplicationManager() {
        var stationReader = new StationReader("resources/stations.csv");
        var trackReader = new TrackReader("resources/tracks.csv");

        this.stations = stationReader.readStationsFromCSVFile();
        this.tracks = trackReader.readTracksFromCSVFile();

        this.stationMap = new MyHashMap<>(stations.size());

        for (Station station : stations) {
            String code = station.getCode();
            stationMap.add(code, station);
        }

        this.graph = new MyGraph<>(stations.size());
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

    public MyGraph<String> getGraph() {
        return graph; // Бушинка село - Дуня
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

    public MyArrayList<String> determineShortestRoute(String stationCode1, String stationCode2) {
        MyArrayList<String> route = new MyArrayList<>();
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
                route.addLast(edge.from());
            }
            System.out.println("Total distance: " + totalDistance);
        } else {
            System.out.println("No route found, try again!");
        }

        return route;
    }

    public SaxGraph<String> showMCST() {
        SaxGraph<String> result = graph.minimumCostSpanningTree();

        double totalLength = 0.0;
        int connectionCount = 0;

        for (String node : result) {
            for (SaxGraph.DirectedEdge<String> edge : result.getEdges(node)) {
                System.out.println(edge.from() + " <-> " + edge.to() + " (distance: " + edge.weight() + ")");
                connectionCount++;
                totalLength += edge.weight();
            }
        }

        System.out.println("Total connections: " + connectionCount);
        System.out.println("Total length: " + totalLength);

        return result;
    }
}
