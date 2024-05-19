package project.se.kth.iv1350.integration;

/**
 * Exception indicating that the specified item could not be found.
 */
public class ItemNotFoundException extends Exception {
    /**
     * Constructs a new ItemNotFoundException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public ItemNotFoundException(String message){
        super(message); 
    }
}

