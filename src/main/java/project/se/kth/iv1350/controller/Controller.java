package project.se.kth.iv1350.controller;

import java.util.ArrayList;
import java.util.List;

import project.se.kth.iv1350.integration.DatabaseErrorException;
import project.se.kth.iv1350.integration.ExternalAccountingSystem;
import project.se.kth.iv1350.integration.ItemNotFoundException;
import project.se.kth.iv1350.integration.ItemRegistry;
import project.se.kth.iv1350.integration.Printer;
import project.se.kth.iv1350.model.CashPayment;
import project.se.kth.iv1350.model.CashRegister;
import project.se.kth.iv1350.model.Receipt;
import project.se.kth.iv1350.model.RevenueObserver;
import project.se.kth.iv1350.model.Sale;
import project.se.kth.iv1350.model.SaleDTO;
import project.se.kth.iv1350.model.Total;
import project.se.kth.iv1350.util.LogManager;

/**
 * The Controller class manages the flow of operations in the point of sale
 * system.
 */
public class Controller {
    private ItemRegistry itemRegistry;
    private Sale sale;
    private CashRegister cashRegister;
    private CashPayment cashPayment;
    private ExternalAccountingSystem externalAccountingSystem;
    private Printer printer;
    private LogManager logManager;
    private List<RevenueObserver> revenueObserver = new ArrayList<>();

    /**
     * Constructs a Controller object with default configurations.
     */
    public Controller() {
        itemRegistry = new ItemRegistry();
        cashRegister = new CashRegister();
        externalAccountingSystem = new ExternalAccountingSystem();
        printer = new Printer();
        logManager = new LogManager();

    }

    /**
     * Starts a new sale transaction.
     */
    public void startSale() {
        sale = new Sale();
        sale.addRevenueObserver(revenueObserver);
    }

    /**
     * Registers an item in the current sale transaction.
     *
     * @param itemId The ID of the item to be registered.
     * @return The SaleDTO object representing the registered item.
     * @throws ItemNotFoundException    If the item is not found.
     * @throws ConnectionErrorException If a connection to the database fails.
     */
    public SaleDTO registerItem(String itemId) throws ItemNotFoundException, ConnectionErrorException {
        try {
            SaleDTO saleDTO = sale.addItem(itemId, itemRegistry);
            return saleDTO;
        } catch (DatabaseErrorException e) {
            logManager.logMessage(e);
            throw new ConnectionErrorException("Connection to database failed");
        }
    }

    /**
     * Ends the current sale transaction and retrieves the total cost.
     *
     * @return The Total object representing the total cost of the sale.
     */
    public Total endSale() {
        return sale.getTotal();
    }

    /**
     * Processes the payment for the current sale transaction.
     *
     * @param paidAmount The amount paid by the customer.
     */
    public void processPayment(double paidAmount) {
        cashPayment = new CashPayment(paidAmount);
        sale.paySale(cashPayment);
        cashRegister.addPaymentToRegister(cashPayment);
        externalAccountingSystem.updateAccountingSystem(sale.getItemList());
        itemRegistry.updateInventory(sale.getItemList());
        Receipt receipt = new Receipt(sale);
        printer.printReceipt(receipt);
    }

    /**
     * Adds a revenue observer to the list of observers.
     *
     * @param observer The revenue observer to be added.
     */
    public void addRevenueObserver(RevenueObserver observer) {
        revenueObserver.add(observer);
    }

}
