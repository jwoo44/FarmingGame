
/**
 * This represents a seed.
 *
 * @author Ali.bab.la
 * @version 1.0
 */
public class Seed extends Item {
    private final String[] possibleSeeds = {
        "Wheat seed", "Rice seed", "Potato seed", "Barley seed"
    };

    private final double universalBasePrice = 10;

    /**
     * Constructor for a seed object. Initializes the name of the seed and its quantity.
     *
     * @param name the name of the seed
     * @param quantity the initial amount of the seed present
     */
    public Seed(String name, int quantity) {
        super(quantity, name);
    }

    /**
     * Generates a random seed.
     * @param quantity number of seeds
     */
    public Seed(int quantity) {
        super(quantity);
        this.name = possibleSeeds[(int) (Math.random() * 4)];
    }

    /** Removes a single seed from the quantity. */
    public void useSeed() {
        if (!(this.quantity <= 0)) {
            this.quantity = this.quantity - 1;
        }
    }

    /**
     * Returns the status of the seed quantity (true if no seed remains).
     *
     * @return true if no seed remains
     */
    public boolean outOfSeed() {
        return (quantity <= 0);
    }

    public double getBasePrice() {
        return universalBasePrice;
    }

    /**
     * Returns the seed price (pre-difficulty) based on the quantity and base price.
     *
     * @return the price of the seed (quantity-based coefficient times base)
     */
    public double getPrice() {
        return this.getPriceCoefficient() * universalBasePrice;
    }

    /**
     * Calculates the coefficient of the seed's price based on seed quantity.<p>
     * Formula: coefficient = (1/sqrt(5)) * sqrt(15 - quantity) for quantity < 14<p>
     * Quantity should not be less than 0. This ensures that coefficient = 1 for quantity = 10.
     *
     * @return the coefficient of the seed's price based on seed quantity
     */
    private double getPriceCoefficient() {
        double coefficient = 1 / (Math.sqrt(5));
        if (quantity >= 14) {
            return coefficient;
        }
        return (coefficient * Math.sqrt(15 - quantity));
    }

    /**
     * Removes and returns a single seed from the quantity.
     *
     * @return a seed with the same name and 1 quantity, null if not enough
     */
    public Seed removeOneSeed() {
        if (!(this.quantity <= 0)) {
            this.quantity = this.quantity - 1;
            return new Seed(name, 1);
        } else {
            return null;
        }
    }
}