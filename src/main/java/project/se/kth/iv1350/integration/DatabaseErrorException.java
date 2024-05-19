package project.se.kth.iv1350.integration;
/**
 * Exception indicating a failure in connecting to the database.
 */
public class DatabaseErrorException extends Exception {
    /**
     * Constructs a new DatabaseErrorException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public DatabaseErrorException(String message) {
        super(message);
    }
}
