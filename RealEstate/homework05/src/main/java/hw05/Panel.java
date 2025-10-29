package hw05;

public class Panel extends RealEstate implements PanelInterface {

    private int floor;          // which floor
    private boolean isInsulated;

    public Panel(String city, double price, int sqm, double numberOfRooms, Genre genre,
                 int floor, boolean isInsulated) {
        super(city, price, sqm, numberOfRooms, genre);
        this.floor = floor;
        this.isInsulated = isInsulated;
    }

    @Override
    public int getTotalPrice() {
        // start from the RealEstate calculation (includes city modifier)
        double total = super.getTotalPrice();

        // floor modifiers: 0-2 => +5%, floor 10 => -5%
        if (floor >= 0 && floor <= 2) total *= 1.05;
        if (floor == 10) total *= 0.95;

        // insulation: +5%
        if (isInsulated) total *= 1.05;

        return (int)Math.round(total);
    }

    @Override
    public boolean hasSameAmount(RealEstate other) {
        return other != null && this.getTotalPrice() == other.getTotalPrice();
    }

    @Override
    public int roomprice() {
        // base price per room = (price per sqm * sqm) / rooms (no city/floor/insulation modifiers)
        double rooms = getNumberOfRooms();
        if (rooms <= 0) return 0;
        double base = getPrice() * getSqm();
        return (int)Math.round(base / rooms);
    }

    @Override
    public String toString() {
        return "Panel{" +
                "city='" + getCity() + '\'' +
                ", pricePerSqm=" + getPrice() +
                ", sqm=" + getSqm() +
                ", rooms=" + getNumberOfRooms() +
                ", genre=" + getGenre() +
                ", floor=" + floor +
                ", insulated=" + isInsulated +
                ", totalPrice=" + getTotalPrice() +
                ", avgSqmPerRoom=" + String.format("%.2f", averageSqmPerRoom()) +
                '}';
    }
}
