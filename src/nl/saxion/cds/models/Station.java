package nl.saxion.cds.models;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.solution.MyArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Station {
    private String code;
    private String name;
    private String country;
    private String type;
    private double latitude;
    private double longitude;

    public Station(String code, String name, String country, String type, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static MyArrayList<Station> readStationsFromCSVFile(String filename) {
        MyArrayList<Station> stations = new MyArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            sc.nextLine();

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] values = line.split(",");

                String code = values[0];
                String name = values[1];
                String country = values[2];
                String type = values[3];
                double latitude = Double.parseDouble(values[4]);
                double longitude = Double.parseDouble(values[5]);

                stations.addLast(new Station(code, name, country, type, latitude, longitude));
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        return stations;
    }
}
