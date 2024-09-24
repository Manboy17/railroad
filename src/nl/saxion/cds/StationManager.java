package nl.saxion.cds;

import nl.saxion.cds.models.Station;
import nl.saxion.cds.models.Track;
import nl.saxion.cds.solution.MyArrayList;
import nl.saxion.cds.solution.MyHashMap;

public class StationManager {
    MyArrayList<Station> stations;
    MyArrayList<Track> tracks;
    private MyHashMap<String, Station> stationMap;

    public StationManager() {
        stations = Station.readStationsFromCSVFile("resources/stations.csv");
        tracks = Track.readTracksFromCSVFile("resources/tracks.csv");

        stationMap = new MyHashMap<>(stations.size());

        for (Station station: stations) {
            stationMap.add(station.getCode(), station);
        }
        System.out.println(stationMap.size());
    }

    public MyArrayList<Station> getStations() {
        return stations;
    }

    public MyArrayList<Track> getTracks() {
        return tracks;
    }

    public Station findStationByCode(String code) {
//        return stationMap.get(code);
        return null;
    }

    public MyArrayList<Station> findStationsByName(String name) {
        return null;
    }

    public MyArrayList<Station> findStationsByType(String type) {
        return null;

    }

}
