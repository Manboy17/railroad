package nl.saxion.cds.models;

import nl.saxion.cds.collection.SaxList;
import nl.saxion.cds.solution.Coordinate;
import nl.saxion.cds.solution.MyArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a station
 */
public class Station {
    private String code;
    private String name;
    private String country;
    private String type;
    private Coordinate coordinate;

    public Station(String code, String name, String country, String type, double latitude, double longitude) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.type = type;
        this.coordinate = new Coordinate(latitude, longitude);
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

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
