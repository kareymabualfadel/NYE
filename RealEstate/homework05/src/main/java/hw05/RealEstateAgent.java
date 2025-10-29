package hw05;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class RealEstateAgent {

    // TreeSet with a stable comparator: by total price, then city, then sqm, then rooms
    private static final TreeSet<RealEstate> stock = new TreeSet<>(
            Comparator.<RealEstate>comparingInt(RealEstate::getTotalPrice)
                    .thenComparing(RealEstate::getCity, String.CASE_INSENSITIVE_ORDER)
                    .thenComparingInt(RealEstate::getSqm)
                    .thenComparingDouble(RealEstate::getNumberOfRooms)
    );

    public static void main(String[] args) throws Exception {
        // 1) Load the file from resources
        List<String> lines = readResourceLines("realestates.txt");
        for (String line : lines) {
            if (line == null || line.isBlank() || line.trim().startsWith("#")) continue;
            parseAndAdd(line.trim());
        }

        // 2) Compute results
        // The average square meter price of real estate (mean of price per sqm)
        double avgSqmPrice = stock.stream().mapToDouble(RealEstate::getPrice).average().orElse(0.0);

        // The price of the cheapest property (by total price)
        int cheapest = stock.stream().mapToInt(RealEstate::getTotalPrice).min().orElse(0);

        // The average sqm value per room of the most expensive apartment in Budapest
        Optional<RealEstate> mostExpBpCondo =
                stock.stream()
                        .filter(re -> re.getCity().equalsIgnoreCase("Budapest")
                                && re.getGenre() == RealEstate.Genre.CONDOMINIUM)
                        .max(Comparator.comparingInt(RealEstate::getTotalPrice));
        double avgSqmPerRoomMostExpBp =
                mostExpBpCondo.map(RealEstate::averageSqmPerRoom).orElse(0.0);

        // The total price of the properties
        long totalPriceAll = stock.stream().mapToLong(RealEstate::getTotalPrice).sum();

        // List of condominium properties whose total price does not exceed the average price of properties
        double averageTotalPrice = stock.stream().mapToDouble(RealEstate::getTotalPrice).average().orElse(0.0);
        List<RealEstate> condosBelowAvg = stock.stream()
                .filter(re -> re.getGenre() == RealEstate.Genre.CONDOMINIUM
                        && re.getTotalPrice() <= averageTotalPrice)
                .collect(Collectors.toList());

        // (Spec says to print again avg sqm price and total price – we’ll include them once more at the end)
        double avgSqmPriceAgain = avgSqmPrice;
        long totalPriceAgain = totalPriceAll;

        // 3) Print to screen & write to file
        List<String> out = new ArrayList<>();
        out.add(String.format(Locale.US, "Average price per sqm: %.2f", avgSqmPrice));
        out.add(String.format(Locale.US, "Cheapest property total price: %d", cheapest));
        out.add(String.format(Locale.US, "Avg sqm per room of most expensive Budapest condominium: %.2f",
                avgSqmPerRoomMostExpBp));
        out.add(String.format(Locale.US, "Sum of total prices: %d", totalPriceAll));
        out.add("Condominiums with total price <= overall average:");
        for (RealEstate re : condosBelowAvg) out.add("  - " + re.toString());
        out.add(String.format(Locale.US, "Average price per sqm (again): %.2f", avgSqmPriceAgain));
        out.add(String.format(Locale.US, "Sum of total prices (again): %d", totalPriceAgain));

        // print
        out.forEach(System.out::println);

        // write
        Path output = Paths.get("outputRealEstate.txt"); // project root
        try (BufferedWriter bw = Files.newBufferedWriter(output)) {
            for (String s : out) { bw.write(s); bw.newLine(); }
        }
        System.out.println("\nResults written to " + output.toAbsolutePath());
    }

    // --------- helpers ---------

    private static List<String> readResourceLines(String name) throws IOException {
        // Try classpath (resources) first
        InputStream is = RealEstateAgent.class.getClassLoader().getResourceAsStream(name);
        if (is != null) {
            try (Scanner sc = new Scanner(is)) {
                List<String> list = new ArrayList<>();
                while (sc.hasNextLine()) list.add(sc.nextLine());
                return list;
            }
        }
        // fallback to a file in working directory
        Path p = Paths.get(name);
        return Files.readAllLines(p);
    }

    private static void parseAndAdd(String line) {
        String[] parts = line.split("#");
        if (parts.length < 6) return;

        String className = parts[0].trim().toUpperCase(Locale.ROOT);
        String city = parts[1].trim();
        double price = Double.parseDouble(parts[2].trim());
        int sqm = Integer.parseInt(parts[3].trim());
        double rooms = Double.parseDouble(parts[4].trim());

        // accept both CONDOMINIUM or FLAT in input
        String genreRaw = parts[5].trim().toUpperCase(Locale.ROOT);
        if (genreRaw.equals("FLAT")) genreRaw = "CONDOMINIUM";
        RealEstate.Genre genre = RealEstate.Genre.valueOf(genreRaw);

        if (className.equals("PANEL")) {
            // floor and isInsulated are present
            if (parts.length < 8) throw new IllegalArgumentException("Panel needs floor & isInsulated: " + line);
            int floor = Integer.parseInt(parts[6].trim());
            boolean insulated = yesNoToBool(parts[7].trim());
            stock.add(new Panel(city, price, sqm, rooms, genre, floor, insulated));
        } else {
            // treat anything else as RealEstate
            stock.add(new RealEstate(city, price, sqm, rooms, genre));
        }
    }

    private static boolean yesNoToBool(String s) {
        String x = s.toLowerCase(Locale.ROOT);
        return x.equals("yes") || x.equals("true") || x.equals("y") || x.equals("1");
    }
}
