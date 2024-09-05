package nl.saxion.cds;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.models.Station;
import nl.saxion.cds.models.Track;

public class StationManager {
    SaxList<Station> stations;
    SaxList<Track> tracks;

    public StationManager() {
        stations = Station.readStationsFromCSVFile("resources/stations.csv");
        tracks = Track.readTracksFromCSVFile("resources/tracks.csv");
    }

    public SaxList<Station> getStations() {
        return stations;
    }

    public SaxList<Track> getTracks() {
        return tracks;
    }


}
