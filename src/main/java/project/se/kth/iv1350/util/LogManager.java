package project.se.kth.iv1350.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * The LogManager class handles logging of exceptions to a file.
 */
public class LogManager {
    private PrintWriter logStream;

    /**
     * Constructs a LogManager object and initializes the logStream PrintWriter.
     */
    public LogManager() {
        try {
            logStream = new PrintWriter(new FileWriter("logException.txt"), true);
        } catch (IOException e) {
            System.out.println("Can not log");
            e.printStackTrace();
        }
    }

    /**
     * Sets the PrintWriter for logging messages.
     *
     * @param logStream The PrintWriter object to set.
     */
    public void setLogStream(PrintWriter logStream) {
        this.logStream = logStream;
    }

    /**
     * Logs the message of the provided exception.
     *
     * @param e The Exception object to log.
     */
    public void logMessage(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(createTime());
        stringBuilder.append(", Exception was thrown: ");
        stringBuilder.append(e.getMessage());
        logStream.println(stringBuilder);
        e.printStackTrace(logStream);
        logStream.println("\n");
    }

    /**
     * Creates a formatted string representing the current time.
     *
     * @return A string representing the current time.
     */
    private String createTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
        return localDateTime.format(formatter);
    }
}
