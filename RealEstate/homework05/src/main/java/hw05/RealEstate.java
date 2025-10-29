package hw05;

import java.util.Locale;
import java.util.Objects;

public class RealEstate implements PropertyInterface {
    public enum Genre { FAMILYHOUSE, CONDOMINIUM, FARM }

    private String city;           // textual
    private double price;          // price per sqm
    private int sqm;               // area
    private double numberOfRooms;  // real (can be e.g. 2.5)
    private Genre genre;

    public RealEstate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    // Copy constructor (useful for comparisons)
    public RealEstate(RealEstate other) {
        this(other.city, other.price, other.sqm, other.numberOfRooms, other.genre);
    }

    // ---------- Interface implementations ----------
    @Override
    public void makeDiscount(int percent) {
        if (percent < 0) return;
        price = price * (100.0 - percent) / 100.0;
    }

    @Override
    public int getTotalPrice() {
        double base = price * sqm;
        // City modifiers: Budapest +30%, Debrecen +20%, Nyíregyháza +15%
        String c = city.toLowerCase(Locale.ROOT);
        if (c.equals("budapest")) base *= 1.30;
        else if (c.equals("debrecen")) base *= 1.20;
        else if (c.equals("nyíregyháza") || c.equals("nyiregyhaza")) base *= 1.15;
        // others: +0%
        return (int)Math.round(base);
    }

    @Override
    public double averageSqmPerRoom() {
        return numberOfRooms > 0 ? ((double) sqm) / numberOfRooms : 0.0;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "city='" + city + '\'' +
                ", pricePerSqm=" + price +
                ", sqm=" + sqm +
                ", rooms=" + numberOfRooms +
                ", genre=" + genre +
                ", totalPrice=" + getTotalPrice() +
                ", avgSqmPerRoom=" + String.format(Locale.US, "%.2f", averageSqmPerRoom()) +
                '}';
    }

    // ---------- Getters ----------
    public String getCity() { return city; }
    public double getPrice() { return price; }
    public int getSqm() { return sqm; }
    public double getNumberOfRooms() { return numberOfRooms; }
    public Genre getGenre() { return genre; }

    // ---------- Helpers ----------
    // We’ll use equals/hashCode so TreeSet + contains work reliably with our comparator
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealEstate)) return false;
        RealEstate that = (RealEstate) o;
        return sqm == that.sqm &&
                Double.compare(price, that.price) == 0 &&
                Double.compare(numberOfRooms, that.numberOfRooms) == 0 &&
                Objects.equals(city, that.city) &&
                genre == that.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, price, sqm, numberOfRooms, genre);
    }
}
