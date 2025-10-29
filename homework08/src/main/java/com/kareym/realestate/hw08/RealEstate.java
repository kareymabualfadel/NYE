package com.kareym.realestate.hw08;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a real estate item with basic attributes and price calculation.
 * <p>
 * This class is used for Homework 08 and demonstrates:
 * - Logging (INFO for method calls, SEVERE for exceptions)
 * - JavaDoc documentation for methods
 * </p>
 */
public class RealEstate {

    private static final Logger log = Logger.getLogger(RealEstate.class.getName());

    private String city;
    private double pricePerSqm;
    private int sqm;
    private double numberOfRooms;
    private Genre genre;

    /**
     * Creates a new RealEstate object with the given parameters.
     *
     * @param city          The city where the property is located.
     * @param pricePerSqm   Price per square meter.
     * @param sqm           Total area in square meters.
     * @param numberOfRooms Number of rooms (can be fractional).
     * @param genre         Type of property.
     * @throws InvalidPropertyException if any field is invalid.
     */
    public RealEstate(String city, double pricePerSqm, int sqm, double numberOfRooms, Genre genre)
            throws InvalidPropertyException {
        log.info("Creating RealEstate object");
        setCity(city);
        setPricePerSqm(pricePerSqm);
        setSqm(sqm);
        setNumberOfRooms(numberOfRooms);
        setGenre(genre);
        log.info("RealEstate object created successfully");
    }

    /** Calculates total property price. */
    public double totalPrice() {
        log.info("Calculating total price");
        return pricePerSqm * sqm;
    }

    // ------------------- Setters with validation -------------------

    public void setCity(String city) throws InvalidPropertyException {
        log.info("setCity() called");
        try {
            if (city == null || city.isBlank())
                throw new InvalidPropertyException("City cannot be empty");
            this.city = city.trim();
        } catch (InvalidPropertyException e) {
            log.log(Level.SEVERE, "Invalid city", e);
            throw e;
        }
    }

    public void setPricePerSqm(double pricePerSqm) throws InvalidPropertyException {
        log.info("setPricePerSqm() called");
        try {
            if (pricePerSqm <= 0)
                throw new InvalidPropertyException("Price per sqm must be positive");
            this.pricePerSqm = pricePerSqm;
        } catch (InvalidPropertyException e) {
            log.log(Level.SEVERE, "Invalid pricePerSqm", e);
            throw e;
        }
    }

    public void setSqm(int sqm) throws InvalidPropertyException {
        log.info("setSqm() called");
        try {
            if (sqm <= 0)
                throw new InvalidPropertyException("Square meters must be greater than 0");
            this.sqm = sqm;
        } catch (InvalidPropertyException e) {
            log.log(Level.SEVERE, "Invalid sqm", e);
            throw e;
        }
    }

    public void setNumberOfRooms(double numberOfRooms) throws InvalidPropertyException {
        log.info("setNumberOfRooms() called");
        try {
            if (numberOfRooms <= 0)
                throw new InvalidPropertyException("Number of rooms must be positive");
            this.numberOfRooms = numberOfRooms;
        } catch (InvalidPropertyException e) {
            log.log(Level.SEVERE, "Invalid numberOfRooms", e);
            throw e;
        }
    }

    public void setGenre(Genre genre) throws InvalidPropertyException {
        log.info("setGenre() called");
        try {
            if (genre == null)
                throw new InvalidPropertyException("Genre must not be null");
            this.genre = genre;
        } catch (InvalidPropertyException e) {
            log.log(Level.SEVERE, "Invalid genre", e);
            throw e;
        }
    }

    // ------------------- Getters -------------------

    public String getCity() {
        log.info("getCity() called");
        return city;
    }

    public double getPricePerSqm() {
        log.info("getPricePerSqm() called");
        return pricePerSqm;
    }

    public int getSqm() {
        log.info("getSqm() called");
        return sqm;
    }

    public double getNumberOfRooms() {
        log.info("getNumberOfRooms() called");
        return numberOfRooms;
    }

    public Genre getGenre() {
        log.info("getGenre() called");
        return genre;
    }

    // ------------------- Object overrides -------------------

    @Override
    public String toString() {
        log.info("toString() called");
        return "RealEstate{" +
                "city='" + city + '\'' +
                ", pricePerSqm=" + pricePerSqm +
                ", sqm=" + sqm +
                ", numberOfRooms=" + numberOfRooms +
                ", genre=" + genre +
                ", totalPrice=" + totalPrice() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        log.info("equals() called");
        if (this == o) return true;
        if (!(o instanceof RealEstate that)) return false;
        return Double.compare(that.pricePerSqm, pricePerSqm) == 0 &&
                sqm == that.sqm &&
                Double.compare(that.numberOfRooms, numberOfRooms) == 0 &&
                Objects.equals(city, that.city) &&
                genre == that.genre;
    }

    @Override
    public int hashCode() {
        log.info("hashCode() called");
        return Objects.hash(city, pricePerSqm, sqm, numberOfRooms, genre);
    }
}
