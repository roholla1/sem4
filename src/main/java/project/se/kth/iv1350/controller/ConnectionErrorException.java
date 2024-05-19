package project.se.kth.iv1350.controller;

/**
 * Signals that an error occurred while attempting to connect to the database.
 */
public class ConnectionErrorException extends Exception {
    
    /**
     * Constructs a ConnectionErrorException with the specified detail message.
     *
     * @param message the detail message
     */
    public ConnectionErrorException(String message) {
        super(message); 
    }
}

