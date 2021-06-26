/**
 *  This represents a player.
 *
 *  @author Ali.bab.la
 *  @version 1.0
 */
public class Player {
    private String name;
    private double money = 0;

    /**
     * Reduces the player's money count by the specified amount.
     *
     * @param money the amount of money to be subtracted
     */
    public void spendMoney(double money) {
        this.money -= money;
    }

    /**
     * Increases the player's money count by the specified amount.
     *
     * @param money the amount of money to be added
     */
    public void gainMoney(double money) {
        this.money += money;
    }

    /**
     * Sets the name of the player.
     *
     * @param name the player's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the player's current money count.
     *
     * @return the amount of money the player has
     */
    public double getMoney() {
        return money;
    }

    /**
     * Gets a string that represents the player's current money count.<p>
     * Format: "Current money: $[money amount to two decimal places]"
     *
     * @return a string containing the truncated money amount and description
     */
    public String getMoneyToString() {
        return "Current money: $" + String.format("%.2f", money);
    }

    /**
     * Gets the player's current name.
     *
     * @return the current name of the player
     */
    public String getName() {
        return name;
    }
}