package nl.saxion.cds;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.models.Station;
import nl.saxion.cds.models.Track;

public class TrackManager {
    public static void main(String[] args) {
        StationManager stationManager = new StationManager();
        SaxList<Station> stations = stationManager.getStations();
        SaxList<Track> tracks = stationManager.getTracks();
        System.out.println(stations.size()); // 578
        System.out.println(tracks.size()); // 2488
    }
}
