import javafx.scene.layout.VBox;
import java.util.ArrayList;


public class Inventory {
    private ArrayList<Item> inventory;
    private VBox inventoryBox = new VBox();
    private final int capacity = 25;

    public Inventory() {
        inventory = new ArrayList<>();
    }

    public void addItem(Item add) {
        for (Item i : inventory) {
            if (i.equals(add)) {
                i.setQuantity(i.getQuantity() + add.getQuantity());
                return;
            }
        }
        inventory.add(add);
    }

    public void display() {
        inventoryBox.getChildren().clear();
        for (Item i: inventory) {
            if (!i.isOut()) {
                i.updateDisplay();
                inventoryBox.getChildren().add(i.getDisplay());
            }
        }
    }

    public VBox getInventoryBox() {
        return inventoryBox;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isFull() {
        int quantity = 0;
        for (Item i : inventory) {
            quantity += i.quantity;
        }

        return quantity >= capacity;
    }
}
