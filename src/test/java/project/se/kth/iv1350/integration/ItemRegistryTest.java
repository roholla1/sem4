package project.se.kth.iv1350.integration;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.se.kth.iv1350.model.SaleDTO;

public class ItemRegistryTest {
    private ItemRegistry itemRegistry;
    private ItemDTO item;

    @BeforeEach
    void setUp() {
        itemRegistry = new ItemRegistry();
    }

    @Test
    void testSearchItemById_ItemExists() throws DatabaseErrorException, ItemNotFoundException {
        item = new ItemDTO("abc123", "item1", 28.20, 6, "some description");
        ItemDTO result = itemRegistry.searchItemById("abc123");
        assertEquals(item, result, "Returned item's ID should match the searched ID.");
    }

    @Test
    public void testSearchItemById_ItemDoesNotExist() {
        String itemId = "nonexistent";
        ItemDTO result = null;
        try {
            result = itemRegistry.searchItemById(itemId);
            fail("Expected ItemNotFoundException, but item was found");
        } catch (ItemNotFoundException ex) {
            assertTrue(ex.getMessage().contains(itemId), "Item ID in exception message doesn't match");
        } catch (DatabaseErrorException e) {
            fail("Unexpected DatabaseErrorException occurred");
        }

        assertNull(result, "No result should be returned if item does not exist");
    }

    @Test
    public void testSearchItemById_DatabaseError() {
        String itemId = "connection_error";
        ItemDTO result = null; 
        try {
            result = itemRegistry.searchItemById(itemId);
            fail("Expected DatabaseErrorException, but no exception was thrown");
        } catch (DatabaseErrorException ex) {
            assertTrue(ex.getMessage().contains("Simulated database connection error"), "Wrong error message or cause");
        } catch (ItemNotFoundException e) {
            fail("Unexpected ItemNotFoundException occurred");
        }
        assertNull(result, "No result should be returned if item does not exist");
    }

    @Test
    public void testUpdateInventory_ItemRemoved() {
        List<SaleDTO> saleItemsToRemove = new ArrayList<>();
        saleItemsToRemove.add(new SaleDTO(new ItemDTO("abc123",
                "item1", 28.20, 6, "some description"), 0.0, 0.0, 0));
        itemRegistry.updateInventory(saleItemsToRemove);

        boolean isItemRemoved = itemRegistry.getItemsPresentInRegistry().stream()
                .noneMatch(item -> item.getItemId().equals("abc123"));
        assertTrue(isItemRemoved, "Item with ID 'abc123' should be removed from the inventory.");
    }
}
