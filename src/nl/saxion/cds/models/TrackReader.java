package nl.saxion.cds.models;

import nl.saxion.cds.solution.MyArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TrackReader {
    private String filename;

    public TrackReader(String filename) {
        this.filename = filename;
    }

    public MyArrayList<Track> readTracksFromCSVFile() {
        MyArrayList<Track> tracks = new MyArrayList<>();
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
