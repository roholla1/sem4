package project.se.kth.iv1350.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.se.kth.iv1350.integration.DatabaseErrorException;
import project.se.kth.iv1350.integration.ItemNotFoundException;
import project.se.kth.iv1350.integration.ItemRegistry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CashPaymentTest {
    private ItemRegistry itemRegistry;
    private Sale sale;
    private CashPayment cashPayment;

    @BeforeEach
    void setUp() {
        itemRegistry = new ItemRegistry();
        sale = new Sale();
        cashPayment = new CashPayment(200);

    }

    @AfterEach
    void tearDown() {
        itemRegistry = null;
        sale = null;
        cashPayment = null;
    }

    @Test
    void testCalculateTotalCost() {
        try {
            sale.addItem("abc123", itemRegistry);
        } catch (ItemNotFoundException e) {
            fail("Excetion occured");
            e.printStackTrace();
        } catch (DatabaseErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }

        cashPayment.calculateTotalCost(sale);
        double expectedTotalCost = sale.getTotal().getTotalPrice();
        assertEquals(expectedTotalCost, cashPayment.getTotalCost(),
                "Total cost should match the sale total price");
    }

    @Test
    void testCalculateTotalCostWithZeroItemInSale() {
        try {
            sale.addItem("", itemRegistry);
        } catch (ItemNotFoundException e) {
            assertEquals(0.0, 0.0);
            e.printStackTrace();
        } catch (DatabaseErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }
        cashPayment.calculateTotalCost(sale);
        double expectedTotalCost = 0.0;
        assertEquals(expectedTotalCost, cashPayment.getTotalCost(),
                "Total cost should match the sale total price");
    }

    @Test
    void testGetChange() {

        try {
            sale.addItem("abc123", itemRegistry);
        } catch (ItemNotFoundException e) {
            fail("Excetion occured");
            e.printStackTrace();
        } catch (DatabaseErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }

        double totalPrice = sale.getTotal().getTotalPrice();
        cashPayment.calculateTotalCost(sale);

        double expectedChange = cashPayment.getPiadAmount() - totalPrice;
        assertEquals(expectedChange, cashPayment.getChange(),
                "Change amount should be calculated correctly");
    }

    @Test
    void testGetPaidAmount() {
        double paidAmount = 50;
        CashPayment cashPayment = new CashPayment(paidAmount);
        assertEquals(paidAmount, cashPayment.getPiadAmount(),
                "Paid amount should match the constructor parameter");
    }
}
