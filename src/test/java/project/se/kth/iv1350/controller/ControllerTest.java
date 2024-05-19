package project.se.kth.iv1350.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.se.kth.iv1350.integration.ItemNotFoundException;
import project.se.kth.iv1350.model.CashPayment;
import project.se.kth.iv1350.model.CashRegister;
import project.se.kth.iv1350.model.SaleDTO;
import project.se.kth.iv1350.model.Total;

public class ControllerTest {
    private CashRegister cashRegister;
    private CashPayment cashPayment;
    private Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Controller();
        cashPayment = new CashPayment(200);
        cashRegister = new CashRegister();

    }

    @AfterEach
    void tearDown() {
        cashRegister = null;
        cashPayment = null;
    }

    @Test
    public void testStartSale() {
        controller.startSale();
        try {
            SaleDTO saleDTO = controller.registerItem("abc123");
            assertNotNull(saleDTO, "Sale object should be used internally");
        } catch (Exception e) {
            fail("Excetion occured");
            e.printStackTrace();
        }
    }

    @Test
    void testRegisterItem_ValidItem() {
        controller.startSale();
        String itemId = "abc123";
        SaleDTO registeredItem = null;
        try {
            registeredItem = controller.registerItem(itemId);
        } catch (ItemNotFoundException e) {
            fail("Excetion occured");
            e.printStackTrace();
        } catch (ConnectionErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }
        assertNotNull(registeredItem, "Registered item is null.");
    }

    @Test
    void testRegisterItem_InvalidItem() {
        controller.startSale();

        String invalidItemId = "invalidItemId";
        SaleDTO registeredItem = null;
        try {
            registeredItem = controller.registerItem(invalidItemId);
            fail("Non existing item has successfully registered in sale");
        } catch (ItemNotFoundException ex) {
            assertEquals(ex.getMessage(), invalidItemId,
                    "Item ID in exception message should return the invalid item ID");
            ex.printStackTrace();
        } catch (ConnectionErrorException e) {
            assertEquals(e.getMessage(), "Connection to database failed", "The message dosn't match.");
            e.printStackTrace();
        }
        assertNull(registeredItem, "Registered item is not null for invalid item.");
    }

    @Test
    void testToConnectToDatabase() {
        controller.startSale();

        String invalidItemId = "connection_error";
        SaleDTO registeredItem = null;
        try {
            registeredItem = controller.registerItem(invalidItemId);
            fail("Connected to non-existing database");
        } catch (ConnectionErrorException e) {
            assertEquals(e.getMessage(), "Connection to database failed", "The message dosn't match.");
            e.printStackTrace();
        } catch (ItemNotFoundException ex) {
            assertEquals(ex.getMessage(), invalidItemId,
                    "Item ID in exception message should return the invalid item ID");
            ex.printStackTrace();
            assertNull(registeredItem, "Registered item is not null for invalid item.");
        }
    }

    @Test
    void testEndSale() {
        controller.startSale();
        Total total = controller.endSale();
        assertNotNull(total, "End sale returns null.");
    }

    @Test
    void testPay() throws ItemNotFoundException, ConnectionErrorException{
        controller.startSale();
        SaleDTO saleDTO = controller.registerItem("abc123");
        controller.processPayment(100);
        assertEquals(cashPayment.getTotalCost(), cashRegister.getBalance(), "wrong amount has been added to the balance");
        
    }

}
