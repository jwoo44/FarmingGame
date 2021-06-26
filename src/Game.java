/**
 *  This represents the game.
 *
 *  @author Ali.bab.la
 *  @version 1.0
 */
public class Game {
    //0 = easy, 1 = medium, 2 = hard
    private int difficulty;

    public Game() {
    }

    /**
     * Identifies the selected difficulty and updates the game difficulty to match.<p>
     * "Easy" = 0, "Normal" = 1, and "Hard" (or otherwise) = 2
     *
     * @param difficultyStr the selected difficulty, either "Easy", "Normal", or "Hard"
     */
    public void setDifficulty(String difficultyStr) {
        if (difficultyStr.equals("Easy")) {
            difficulty = 0;
        } else if (difficultyStr.equals("Normal")) {
            difficulty = 1;
        } else {
            difficulty = 2;
        }
    }

    /**
     * Gets the game's current difficulty.
     *
     * @return the game's difficulty, which should be 0, 1, or 2
     */
    public int getDifficulty() {
        return difficulty;
    }

}