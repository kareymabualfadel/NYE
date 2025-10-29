package hw04;

public interface PropertyInterface {
    void makeDiscount(int percent);       // reduce price per sqm by percent
    int getTotalPrice();                  // total price considering city modifiers
    double averageSqmPerRoom();           // sqm / number of rooms
}
