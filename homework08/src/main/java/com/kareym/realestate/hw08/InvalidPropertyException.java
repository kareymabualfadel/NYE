package com.kareym.realestate.hw08;

/**
 * Custom exception thrown when a RealEstate object receives invalid data.
 * <p>
 * Used to signal input validation errors such as negative values or missing fields.
 * </p>
 */
public class InvalidPropertyException extends Exception {

    /**
     * Creates a new InvalidPropertyException with the specified detail message.
     *
     * @param message The detail message that explains the reason for the exception.
     */
    public InvalidPropertyException(String message) {
        super(message);
    }
}
