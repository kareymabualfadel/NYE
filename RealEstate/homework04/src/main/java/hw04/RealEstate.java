package hw04;

public class RealEstate implements PropertyInterface {

    // Enum for Genre
    public enum Genre {
        FAMILYHOUSE,
        CONDOMINIUM,
        FARM
    }

    // Fields
    private String city;
    private double pricePerSqm;
    private int sqm;
    private double numberOfRooms;
    private Genre genre;

    // Constructor
    public RealEstate(String city, double pricePerSqm, int sqm, double numberOfRooms, Genre genre) {
        this.city = city;
        this.pricePerSqm = pricePerSqm;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    // Implement interface methods
    @Override
    public void makeDiscount(int percent) {
        pricePerSqm -= pricePerSqm * percent / 100.0;
    }

    @Override
    public int getTotalPrice() {
        double total = pricePerSqm * sqm;
        switch (city.toLowerCase()) {
            case "budapest":
                total *= 1.30;
                break;
            case "debrecen":
                total *= 1.20;
                break;
            case "nyíregyháza":
            case "nyiregyhaza":
                total *= 1.15;
                break;
        }
        return (int) total;
    }

    @Override
    public double averageSqmPerRoom() {
        return sqm / numberOfRooms;
    }

    @Override
    public String toString() {
        return String.format(
                "%s [city=%s, pricePerSqm=%.2f, sqm=%d, rooms=%.1f, genre=%s, totalPrice=%d, avgSqmPerRoom=%.2f]",
                getClass().getSimpleName(),
                city, pricePerSqm, sqm, numberOfRooms, genre,
                getTotalPrice(), averageSqmPerRoom()
        );
    }

    // Getters
    public String getCity() { return city; }
    public double getPricePerSqm() { return pricePerSqm; }
    public int getSqm() { return sqm; }
    public double getNumberOfRooms() { return numberOfRooms; }
    public Genre getGenre() { return genre; }
}
