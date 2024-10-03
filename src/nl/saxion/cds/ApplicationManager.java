package nl.saxion.cds;

import nl.saxion.cds.models.Station;
import nl.saxion.cds.models.StationReader;
import nl.saxion.cds.models.Track;
import nl.saxion.cds.models.TrackReader;
import nl.saxion.cds.solution.MyArrayList;
import nl.saxion.cds.solution.MyHashMap;

import java.util.Comparator;

public class ApplicationManager implements Runnable {
    MyArrayList<Station> stations;
    MyArrayList<Track> tracks;
    private MyHashMap<String, Station> stationMap;

    @Override
    public void run() {
        System.out.println("SAXION APP IMAGE HERE!");
    }

    public ApplicationManager() {
        var stationReader = new StationReader("resources/stations.csv");
        var trackReader = new TrackReader("resources/tracks.csv");
        stations = stationReader.readStationsFromCSVFile();
        tracks = trackReader.readTracksFromCSVFile();

        stationMap = new MyHashMap<>(stations.size());

        for (Station station: stations) {
            String code = station.getCode();
            stationMap.add(code, station);
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

       for (Station station: stations) {
           if(station.getName().startsWith(name)) {
               matchedStations.addLast(station);
           }
       }

       return matchedStations;
    }

    public MyArrayList<Station> findStationsByType(String type) {
        MyArrayList<Station> foundStations = new MyArrayList<>();

        for (Station station: stations) {
            if(station.getType().equals(type)) {
                foundStations.addLast(station);
            }
        }

        foundStations.simpleSort(Comparator.comparing(Station::getName));

        return foundStations;
    }
}
