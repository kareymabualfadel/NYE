package hw04;

public class Panel extends RealEstate implements PanelInterface {

    private int floor;
    private boolean isInsulated;

    public Panel(String city, double pricePerSqm, int sqm, double numberOfRooms, Genre genre, int floor, boolean isInsulated) {
        super(city, pricePerSqm, sqm, numberOfRooms, genre);
        this.floor = floor;
        this.isInsulated = isInsulated;
    }

    @Override
    public int getTotalPrice() {
        double total = super.getTotalPrice();

        // Apply floor modifiers
        if (floor >= 0 && floor <= 2)
            total *= 1.05;
        else if (floor == 10)
            total *= 0.95;

        // Apply insulation bonus
        if (isInsulated)
            total *= 1.05;

        return (int) total;
    }

    @Override
    public boolean hasSameAmount(RealEstate other) {
        return this.getTotalPrice() == other.getTotalPrice();
    }

    @Override
    public int roomPrice() {
        return (int) (getPricePerSqm() * getSqm() / getNumberOfRooms());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", floor=%d, insulated=%b]", floor, isInsulated);
    }
}
