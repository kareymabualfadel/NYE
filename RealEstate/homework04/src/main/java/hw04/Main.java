package hw04;

public class Main {
    public static void main(String[] args) {
        RealEstate r1 = new RealEstate("Budapest", 250000, 100, 4, RealEstate.Genre.CONDOMINIUM);
        Panel p1 = new Panel("Debrecen", 120000, 35, 2, RealEstate.Genre.CONDOMINIUM, 0, true);

        System.out.println(r1);
        System.out.println(p1);
    }
}
