package project.se.kth.iv1350.integration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import project.se.kth.iv1350.model.SaleDTO;

/**
 * A registry containing items available for sale.
 */
public class ItemRegistry {
    private List<ItemDTO> itemsPresentInRegistry;

    /**
     * Constructs an ItemRegistry and initializes the list of items.
     */
    public ItemRegistry() {
        itemsPresentInRegistry = new ArrayList<>();
        addItemToList();
    }

    /**
     * Searches for an item in the registry by its ID.
     * 
     * @param itemId The ID of the item to search for.
     * @return The ItemDTO object representing the found item.
     * @throws DatabaseErrorException If a simulated database connection error
     *                                occurs.
     * @throws ItemNotFoundException  If the specified item is not found in the
     *                                registry.
     */
    public ItemDTO searchItemById(String itemId) throws DatabaseErrorException, ItemNotFoundException {

        if (itemId.equals("connection_error")) {
            throw new DatabaseErrorException("Simulated database connection error");
        }

        for (ItemDTO item : itemsPresentInRegistry) {
            if (item.getItemId().equals(itemId)) {
                return new ItemDTO(item.getItemId(), item.getItemName(), item.getItemPrice(), item.getItemVAT(),
                        item.getItemDescription());
            }
        }

        throw new ItemNotFoundException(itemId);
    }

    /**
     * Updates the inventory by removing sold items from the registry.
     * 
     * @param itemList The list of items sold in a transaction.
     */
    public void updateInventory(List<SaleDTO> itemList) {
        Iterator<ItemDTO> iterator = itemsPresentInRegistry.iterator();
        while (iterator.hasNext()) {
            ItemDTO item = iterator.next();
            for (SaleDTO saleItem : itemList) {
                ItemDTO saleItemDTO = saleItem.getItemDTO();
                if (item.equals(saleItemDTO)) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    /**
     * Retrieves the list of items in the registry.
     * 
     * @return The list of ItemDTO objects in the registry.
     */
    public List<ItemDTO> getItemsPresentInRegistry() {
        return itemsPresentInRegistry;
    }

    /**
     * Adds initial items to the registry.
     */
    private void addItemToList() {
        itemsPresentInRegistry.add(new ItemDTO("abc123", "item1", 28.20, 6, "some description"));
        itemsPresentInRegistry.add(new ItemDTO("abc123", "item1", 28.20, 6, "some description"));
        itemsPresentInRegistry.add(new ItemDTO("def456", "item2", 14.90, 6, "some description"));
    }
}
