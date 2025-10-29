package com.kareym.realestate.hw08;

import java.util.logging.Logger;

/**
 * Entry point of the RealEstate application for Homework 08.
 * <p>
 * This class initializes logging, creates a sample RealEstate object,
 * and demonstrates logging of method calls and exception handling.
 * </p>
 */
public class Main {

    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        // Initialize logger setup (creates realEstateApp.log)
        LoggerConfig.init();
        log.info("Application started");

        try {
            // Create a valid real estate object
            RealEstate re = new RealEstate("Budapest", 1250.0, 56, 2.0, Genre.APARTMENT);
            System.out.println(re);
            System.out.println("Total price: " + re.totalPrice());

            // Uncomment to test an invalid case:
            // RealEstate invalid = new RealEstate("", -500, -30, 0, null);

        } catch (InvalidPropertyException e) {
            // Already logged inside RealEstate setters, just show message
            System.err.println("Error: " + e.getMessage());
        }

        log.info("Application finished successfully");
    }
}
