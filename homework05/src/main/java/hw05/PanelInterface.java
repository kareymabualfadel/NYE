package hw05;

public interface PanelInterface {
    boolean hasSameAmount(RealEstate other); // compare total prices
    int roomprice();                         // avg price per room (base price * sqm / rooms), no city/floor/insulation modifiers
}
