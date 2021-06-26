import javafx.scene.text.Text;

/**
 * This represents an Item, that can fit in the inventory.
 *
 * @author Ali.bab.la
 * @version 1.0
 */
public abstract class Item {
    protected int quantity;
    protected String name;
    protected Text display = new Text(this.toString());

    public Item() {
    }

    public Item(int quantity) {
        this.quantity = quantity;
    }

    public Item(int quantity, String name) {
        this(quantity);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item other = (Item) o;
        return name.equals(other.name);
    }

    @Override
    public String toString() {
        return this.name + " (" + this.quantity + ")";
    }

    /**
     * Gets the name of the item.
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current quantity of the item.
     *
     * @return the quantity of the item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Changes the name of the seed to the specified name.
     *
     * @param name the new name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the seed quantity to be equal to the specified quantity.
     *
     * @param quantity the new quantity of the item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the display (Text) of the seed.
     *
     * @return the display text of the seed
     */
    public Text getDisplay() {
        return display;
    }

    /** Updates the display of the seed's text to reflect the current status (name/quantity).  */
    public void updateDisplay() {
        display.setText(this.toString());
    }

    public boolean isOut() {
        return quantity == 0;
    }
}
