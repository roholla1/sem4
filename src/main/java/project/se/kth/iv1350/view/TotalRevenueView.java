package project.se.kth.iv1350.view;

import project.se.kth.iv1350.model.RevenueObserver;

/**
 * The TotalRevenueView class implements the RevenueObserver interface
 * to record and display the total income.
 */
public class TotalRevenueView implements RevenueObserver {

    /** The total income recorded by the observer. */
    private double totalIncome = 0;

    /**
     * Records the total revenue and updates the total income.
     *
     * @param totalRevenue The total revenue to be recorded.
     */
    @Override
    public void recordRevenue(double totalRevenue) {
        totalIncome += totalRevenue;
        System.out.println("\nTotal revenue: " + totalIncome + " SEK");
    }
}
