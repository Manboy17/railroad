package nl.saxion.cds;

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
import java.util.Iterator;

public class ApplicationManager {
    private MyArrayList<Station> stations;
    private MyArrayList<Track> tracks;
    private MyHashMap<String, Station> stationMap;
    private MyGraph<String> graph;

    /**
     * Constructor for the ApplicationManager class.
     */
    public ApplicationManager() {
        var stationReader = new StationReader("resources/stations.csv");
        var trackReader = new TrackReader("resources/tracks.csv");

        this.stations = stationReader.readStationsFromCSVFile();
        this.tracks = trackReader.readTracksFromCSVFile();

        this.stationMap = new MyHashMap<>(stations.size());

        for (Station station : stations) {
            if (station.getCountry().equals("NL")) {
                String code = station.getCode();
                stationMap.add(code, station);
            }
        }

        this.graph = new MyGraph<>(stationMap.size());
        initializeGraph();
    }

    /**
     * Initializes the graph by adding all tracks to the graph.
     */
    private void initializeGraph() {
        for (Track track : tracks) {
            String fromCode = track.getFrom();
            String toCode = track.getTo();
            double distance = track.getDistance();

            if (stationMap.contains(fromCode) && stationMap.contains(toCode)) {
                graph.addEdgeBidirectional(fromCode, toCode, distance);
            }
        }
    }

    /**
     * Returns the graph.
     * @return the graph
     */
    public MyGraph<String> getGraph() {
        return graph;
    }


    /**
     * Finds a station by its code.
     * @param code station code
     * @return the found station
     */
    public Station findStationByCode(String code) {
        return stationMap.get(code);
    }

    /**
     * Finds stations by name.
     * @param name station name
     * @return a list of stations
     */
    public MyArrayList<Station> findStationsByName(String name) {
        MyArrayList<Station> matchedStations = new MyArrayList<>();

        for (Station station : stations) {
            if (station.getName().startsWith(name)) {
                matchedStations.addLast(station);
            }
        }

        return matchedStations;
    }

    /**
     * Finds stations by type.
     * @param type station type
     * @return a list of stations
     */
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

    /**
     * Finds stations by country.
     * @param stationCode1 station code 1
     * @param stationCode2 station code 2
     * @return a list of stations as the shortest route
     */
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
                System.out.println(edge.from() + " -> " + edge.to() + " (distance: " + edge.weight() + " km)");
                totalDistance += edge.weight();
                route.addLast(edge.from());
            }
            System.out.println("Total distance: " + totalDistance + " km");
        } else {
            System.out.println("No route found, try again!");
        }

        return route;
    }

    /**
     * Shows the minimum cost spanning tree.
     * @return the minimum cost spanning tree (graph)
     */
    public SaxGraph<String> showMCST() {
        SaxGraph<String> result = graph.minimumCostSpanningTree();

        double totalLength = 0.0;
        int connectionCount = 0;

        Iterator<String> iterator = result.iterator();

        while (iterator.hasNext()) {
            String node = iterator.next();

            for (SaxGraph.DirectedEdge<String> edge : result.getEdges(node)) {
                if (edge.from().compareTo(edge.to()) < 0) {
                    System.out.println(edge.from() + " <-> " + edge.to() + " (distance: " + edge.weight() + " km)");
                    connectionCount++;
                    totalLength += edge.weight();
                }
            }
        }

        System.out.println("Total connections: " + connectionCount);
        System.out.println("Total length: " + totalLength + " km");

        return result;
    }
}
