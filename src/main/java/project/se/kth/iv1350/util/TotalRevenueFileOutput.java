package project.se.kth.iv1350.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import project.se.kth.iv1350.model.RevenueObserver;

/**
 * The TotalRevenueFileOutput class implements the RevenueObserver interface and
 * records the total revenue to a file.
 */
public class TotalRevenueFileOutput implements RevenueObserver {
    private String fileName;
    private double totalRevenue;

    /**
     * Constructs a TotalRevenueFileOutput object with a default file name
     * "total_revenue.txt".
     */
    public TotalRevenueFileOutput() {
        this.fileName = "total_revenue.txt";
        this.totalRevenue = 0.0;
    }

    /**
     * Records the total revenue to the file and adds the provided revenue to the
     * total.
     *
     * @param newRevenue The new revenue to be recorded.
     */
    @Override
    public void recordRevenue(double newRevenue) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            totalRevenue += newRevenue;
            writer.println("Total Revenue: " + totalRevenue + " SEK");
        } catch (IOException e) {
            System.err.println("Error occurred while writing to the file: " + e.getMessage());
        }
    }
}
