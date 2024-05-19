package project.se.kth.iv1350.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.se.kth.iv1350.integration.DatabaseErrorException;
import project.se.kth.iv1350.integration.ItemNotFoundException;
import project.se.kth.iv1350.integration.ItemRegistry;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

public class SaleTest {
    private Sale sale;
    private ItemRegistry itemRegistry;
    private Total total;
    private CashPayment cashPayment;

    @BeforeEach
    void setUp() {
        sale = new Sale();
        itemRegistry = new ItemRegistry();
        total = new Total();
        cashPayment = new CashPayment(200);
    }

    @AfterEach
    void tearDown() {
        sale = null;
        itemRegistry = null;
    }

    @Test
    void testAddItem_ItemExistsInRegistry() throws ItemNotFoundException, DatabaseErrorException {
        String itemId = "abc123";
        SaleDTO result = sale.addItem(itemId, itemRegistry);
        assertNotNull(result, "Item not added to the sale");
    }

    @Test
    void testAddItem_ItemNotInRegistry() {
        String itemId = "nonexistentId";
        SaleDTO result = null;
        try {
            result = sale.addItem(itemId, itemRegistry);
            fail("Expected ItemNotFoundException, but item was found");
        } catch (ItemNotFoundException ex) {
            assertTrue(ex.getMessage().contains(itemId));
        } catch (DatabaseErrorException e) {
            assertTrue(e.getMessage().contains("Simulated database connection error"), 
                "Wrong error message or cause");
        }
        assertNull(result, "Item added to sale when it doesn't exist in the registry");
    }

    @Test
    void testAddItem_ItemAlreadyInSale() {
        String itemId = "abc123";
        SaleDTO initialSaleItem = null; 
        try {
            initialSaleItem = sale.addItem(itemId, itemRegistry);
        } catch (ItemNotFoundException e) {
            fail("Excetion occured");
            e.printStackTrace();
        } catch (DatabaseErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }
        assertNotNull(initialSaleItem, "Item not added to the sale initially");

        SaleDTO updatedSaleItem = null; 
        try {
            updatedSaleItem = sale.addItem(itemId, itemRegistry);
        } catch (ItemNotFoundException e) {
            fail("Excetion occured");
            e.printStackTrace();
        } catch (DatabaseErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }
        assertNotNull(updatedSaleItem, "Item not updated in the sale");
        assertEquals(2, updatedSaleItem.getQuantity(), "Quantity not updated for existing item in the sale");
    }

    @Test
    void testGetTotal_WithItems() {
        String itemId = "abc123";
        try {
            sale.addItem(itemId, itemRegistry);
        } catch (ItemNotFoundException e) {
            fail("Excetion occured");
            e.printStackTrace();
        } catch (DatabaseErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }
        assertNotNull(sale.getTotal(), "Total not calculated with items in the sale");
    }

    @Test
    void testGetTotal_NoItems() {
        String expected = "Total cost (incl VAT): 0.0";
        assertEquals(expected, total.toString(), "Total calculated when there are no items in the sale");
    }

    @Test
    void testPaySale() {
        sale.paySale(cashPayment);
        assertNotNull(sale.getCashPayment(), "Sale payment not processed correctly");
    }

    @Test
    void testToString_WithItems() {
        String itemId = "abc123";
        try {
            sale.addItem(itemId, itemRegistry);
        } catch (ItemNotFoundException e) {
            fail("Excetion occured");
            e.printStackTrace();
        } catch (DatabaseErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }
        sale.paySale(cashPayment);
        String result = sale.toString();
        assertNotNull(result, "String representation not generated with items in the sale");
    }

    @Test
    void testToString_NoItems() {
        String itemId = "";
        try {
            sale.addItem(itemId, itemRegistry);
        } catch (ItemNotFoundException e) {
            assertNotNull("");
            e.printStackTrace();
        } catch (DatabaseErrorException e) {
            fail("Excetion occured");
            e.printStackTrace();
        }
        sale.paySale(cashPayment);
        String result = sale.toString();
        assertNotNull(result, "String representation not generated when there are no items in the sale");
    }


}
