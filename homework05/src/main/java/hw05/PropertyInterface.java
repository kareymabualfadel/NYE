package hw05;

public interface PropertyInterface {
    void makeDiscount(int percent);         // reduce price per sqm by percent
    int getTotalPrice();                     // total price with city/floor/insulation rules
    double averageSqmPerRoom();              // sqm / numberOfRooms
}
