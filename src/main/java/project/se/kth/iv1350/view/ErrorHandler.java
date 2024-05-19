package project.se.kth.iv1350.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * The ErrorHandler class provides methods to display error messages with
 * timestamps.
 */
public class ErrorHandler {

    /**
     * Displays the error message with a timestamp.
     *
     * @param message The error message to be displayed.
     */
    public void displayErrorMessage(String message) {
        StringBuilder builder = new StringBuilder();
        builder.append(createTime());
        builder.append(", Error: ");
        builder.append(message);
        System.out.println(builder);
    }

    /**
     * Creates a timestamp for the current time.
     *
     * @return The timestamp string representing the current time.
     */
    private String createTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return localDateTime.format(formatter);
    }
}
