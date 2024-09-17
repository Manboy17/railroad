package nl.saxion.cds;

import nl.saxion.cds.models.Station;
import nl.saxion.cds.models.Track;
import nl.saxion.cds.solution.MyArrayList;

public class StationManager {
    MyArrayList<Station> stations;
    MyArrayList<Track> tracks;

    public StationManager() {
        stations = Station.readStationsFromCSVFile("resources/stations.csv");
        tracks = Track.readTracksFromCSVFile("resources/tracks.csv");
    }

    public MyArrayList<Station> getStations() {
        return stations;
    }

    public MyArrayList<Track> getTracks() {
        return tracks;
    }

    public Station findStationByCode(String code) {
        return null;
    }

    public MyArrayList<Station> findStationsByName(String name) {
        return null;
    }

    public MyArrayList<Station> findStationsByType(String type) {
        return null;

    }

}
