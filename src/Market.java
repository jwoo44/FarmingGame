import java.util.ArrayList;

/**
 *  This represents the market.
 *
 *  @author Ali.bab.la
 *  @version 1.0
 */
public class Market {
    private ArrayList<Seed> seedList = new ArrayList<>();
    private Game game;
    private double universalSeedBasePrice;
    private double universalCropBasePrice = 50;

    /**
     * Constructor for a new market. Initializes the seed list and get the base price.
     *
     * @param game the main game class
     */
    public Market(Game game) {
        this.game = game;
        seedList.add(new Seed("Wheat seed", 10));
        seedList.add(new Seed("Rice seed", 10));
        seedList.add(new Seed("Potato seed", 10));
        seedList.add(new Seed("Barley seed", 10));
        Seed s = new Seed("", 1);
        universalSeedBasePrice = s.getBasePrice();
    }

    /**
     * Adds additional seeds to the farm's collection of seeds.<p>
     * Will either increase the quantity of a seed type or add a brand new type.
     *
     * @param seed the seed to be added
     */
    public void addSeed(Seed seed) {
        for (Seed s : seedList) {
            if (s.equals(seed)) {
                s.setQuantity(s.getQuantity() + seed.getQuantity());
                return;
            }
        }
        seedList.add(seed);
    }

    /**
     * Returns the price the farm pays for seeds based on the base price and the game difficulty
     *
     * @return the price the farm sells the seeds for (base price / (difficulty + 2))
     */
    public double getBuyPrice() {
        return universalSeedBasePrice / (game.getDifficulty() + 2);
    }

    public double getCropBuyPrice() {
        return (universalCropBasePrice
                * (0.8 + (game.getDifficulty() / 5.0)) * ((Math.random() / 5) + 0.9));
    }

    /**
     * Calculates and returns the price the market sells a seed for.<p>
     * Uses the seeds' getPrice method and the game's difficulty.
     *
     * @param seed the seed of which the price is to be calculated
     * @return price the market sells the seed for (getPrice * (difficulty + 1))
     */
    public double getMarketPrice(Seed seed) {
        return seed.getPrice() * (game.getDifficulty() + 1);
    }

    /**
     * Returns the ArrayList of seeds.
     *
     * @return list of seeds
     */
    public ArrayList<Seed> getSeeds() {
        return seedList;
    }
}
