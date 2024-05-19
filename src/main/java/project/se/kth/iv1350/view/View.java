package project.se.kth.iv1350.view;

import project.se.kth.iv1350.controller.ConnectionErrorException;
import project.se.kth.iv1350.controller.Controller;
import project.se.kth.iv1350.integration.ItemNotFoundException;
import project.se.kth.iv1350.model.SaleDTO;
import project.se.kth.iv1350.model.Total;
import project.se.kth.iv1350.util.LogManager;
import project.se.kth.iv1350.util.TotalRevenueFileOutput;

/**
 * The View class represents the user interface for displaying sale-related
 * information.
 */
public class View {
    private Controller contr;
    private LogManager logStream = new LogManager();
    private ErrorHandler errorHandler = new ErrorHandler();

    /**
     * Constructs a View object with the specified controller.
     *
     * @param contr The Controller object to interact with the sale process.
     */
    public View(Controller contr) {
        this.contr = contr;
        contr.addRevenueObserver(new TotalRevenueView());
        contr.addRevenueObserver(new TotalRevenueFileOutput());
    }

    /**
     * Displays the sale process, including registering items, ending the sale, and
     * processing payment. Handles various exceptions that may occur during the
     * process.
     */
    public void show() {
        try {
            contr.startSale();
            try {
                System.out.println();
                SaleDTO item = contr.registerItem("abc123");
                System.out.println(item + "\n");
                SaleDTO item2 = contr.registerItem("abc123");
                System.out.println(item2 + "\n");
                SaleDTO item3 = contr.registerItem("def456");
                System.out.println(item3 + "\n");
                System.out.println("Scanning an item with an invalid ID.");
                SaleDTO item4 = contr.registerItem("abc112");
                System.out.println(item4 + "\n");
                System.out.println("An item with an invalid ID has been successfully added to the sale.\n");
            } catch (ItemNotFoundException e) {
                System.out.println();
                errorHandler.displayErrorMessage("Invalid identifier, " + e.getMessage());
                System.out.println();
            } catch (ConnectionErrorException e) {
                System.out.println("Connection error occurred: " + e.getMessage());
            }

            try {
                System.out.println("Try to connect to database: ");
                SaleDTO item5;
                try {
                    item5 = contr.registerItem("connection_error");
                    System.out.println(item5);
                } catch (ItemNotFoundException e) {
                    errorHandler.displayErrorMessage("Invalid identifier, " + e.getMessage());
                }

            } catch (ConnectionErrorException ex) {
                errorHandler.displayErrorMessage(ex.getMessage());
                System.out.println();
            }

            System.out.println("End sale:");
            Total totalPrice = contr.endSale();
            System.out.println(totalPrice + "\n");
            System.out.println("Customer pays 100 SEk:");
            System.out.println("Sent sale info to external accounting system.");
            System.out.println("Told to update item Registry");
            contr.processPayment(100);
            System.out.println();
        } catch (Exception e) {
            errorHandler.displayErrorMessage("Something went wrong \n");
            logStream.logMessage(e);
        }
    }

}
