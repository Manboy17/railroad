package nl.saxion.cds.models;

import nl.saxion.cds.solution.MyArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StationReader {
    private String filename;

    public StationReader(String filename) {
        this.filename = filename;
    }


    public MyArrayList<Station> readStationsFromCSVFile() {
        MyArrayList<Station> result = new MyArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            scanner.nextLine();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] lineParts = line.split(",");

                String code = lineParts[0];
                String name = lineParts[1];
                String country = lineParts[2];
                String type = lineParts[3];
                double latitude = Double.parseDouble(lineParts[4]);
                double longitude = Double.parseDouble(lineParts[5]);

                Station newStation = new Station(code, name, country, type, latitude, longitude);
                result.addLast(newStation);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
