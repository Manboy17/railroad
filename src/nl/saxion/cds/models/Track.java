package nl.saxion.cds.models;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.solution.MyArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Track {
    private String from;
    private String to;
    private int cost_unit;
    private double distance;

    public Track(String from, String to, int cost_unit, double distance) {
        this.from = from;
        this.to = to;
        this.cost_unit = cost_unit;
        this.distance = distance;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getCost_unit() {
        return cost_unit;
    }

    public double getDistance() {
        return distance;
    }

    public static SaxList<Track> readTracksFromCSVFile(String filename) {
        SaxList<Track> tracks = new MyArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            sc.nextLine();

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] values = line.split(",");

                String from = values[0];
                String to = values[1];
                int cost_unit = Integer.parseInt(values[2]);
                double distance = Double.parseDouble(values[3]);

                tracks.addLast(new Track(from, to, cost_unit, distance));
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return tracks;
    }
}
