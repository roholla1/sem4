package project.se.kth.iv1350.model;

/**
 * An interface for objects observing and recording revenue updates.
 */
public interface RevenueObserver {
    
    /**
     * Records the total revenue.
     * 
     * @param totalRevenue The total revenue to be recorded.
     */
    void recordRevenue(double totalRevenue); 
}

