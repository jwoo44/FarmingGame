import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 *  This represents a farm plot.
 *
 *  @author Ali.bab.la
 *  @version 1.0
 */
public class FarmPlot {
    private Seed plantedSeed;
    private HBox displayBox = new HBox();
    private int growthProgress;
    private Button displayBut = new Button(getSeedLetter());
    private int waterLevel;

    /**
     * Blank constructor, creates a farm plot with no seed
     */
    public FarmPlot() {
        this(null, 0);
    }

    /**
     * Constructor for a new farm plot. The plot begins with the specified seed planted.<p>
     * Also sets up the display box for the game screen.
     *
     * @param plantedSeed the seed that is planted in the plot
     * @param growthProgress the growth progress (0 = no seed, 1 = seed, 2 = immature, 3 = mature)
     */
    public FarmPlot(Seed plantedSeed, int growthProgress) {
        this.plantedSeed = plantedSeed;
        displayBox.getChildren().add(displayBut);
        displayBut.setFont(Font.font("Courier New", 15));
        displayBox.setPadding(new Insets(2, 2, 1, 2));
        this.growthProgress = growthProgress;
        updateFarmPlotBorder();
        setFarmPlotButton();
    }

    /** Updates the display text of the farm plot with the current seed letter. */
    public void updateDisplay() {
        displayBut.setText(getSeedLetter());
        updateFarmPlotBorder();
    }

    /**
     * Returns the status of the farm plot (true if no seed is planted).
     *
     * @return true if the farm plot has no planted seed, otherwise false
     */
    public boolean isEmpty() {
        return (plantedSeed == null);
    }

    /**
     * Gets the first letter of the planted seed, sandwiched by spaces.<p>
     * If there is no planted seed, the "first letter" is "-".
     *
     * @return the first letter of the planted seed X in the form " X "
     */
    public String getSeedLetter() {
        if (plantedSeed == null) {
            return " - ";
        }
        return " " + plantedSeed.getName().substring(0, 1) + " ";
    }

    public int getGrowthProgress() {
        return growthProgress;
    }

    public void setWaterLevel(int level) {
        waterLevel = level;
    }

    /**
     * Gets the currently planted seed in the plot.
     *
     * @return the seed planted in the plot
     */
    public Seed getSeed() {
        return plantedSeed;
    }

    public void setSeed(Seed plantedSeed) {
        this.plantedSeed = plantedSeed;
    }

    /**
     * Gets the display box (HBox) of the farm plot.
     *
     * @return the display box of the farm plot
     */
    public HBox getDisplay() {
        return displayBox;
    }

    public void updateFarmPlotBorder() {
        if (growthProgress == 0) {
            displayBox.setStyle("-fx-border-color: black; -fx-border-width: 3");
        } else if (growthProgress == 1) {
            displayBox.setStyle("-fx-border-color: red; -fx-border-width: 3");
        } else if (growthProgress == 2) {
            displayBox.setStyle("-fx-border-color: orange; -fx-border-width: 3");
        } else if (growthProgress == 3) {
            displayBox.setStyle("-fx-border-color: green; -fx-border-width: 3");
        } else {
            displayBox.setStyle("-fx-border-color: #89471d; -fx-border-width: 3");
        }
        if (waterLevel < 2) {
            displayBut.setStyle("-fx-text-fill: black");
        } else if (waterLevel < 4) {
            displayBut.setStyle("-fx-text-fill: #33b4a5");
        } else {
            displayBut.setStyle("-fx-text-fill: red");
        }
    }

    public void setFarmPlotButton() {
        displayBut.setOnAction(e -> {
            if (growthProgress == 3 && !MainApp.getInventory().isFull()
                    && !MainApp.getMode().isSelected()) {
                MainApp.getInventory().addItem(harvest());
                plantedSeed = null;
                growthProgress = 0;
            } else if (growthProgress == -1 && !MainApp.getMode().isSelected()) {
                harvest();
                plantedSeed = null;
                growthProgress = 0;
            } else if (growthProgress == 0 && MainApp.getToPlant().getValue() != null
                    && !MainApp.getMode().isSelected()) {
                plantedSeed = MainApp.getToPlant().getValue().removeOneSeed();
                growthProgress = 1;
                MainApp.updateToPlant();
            } else if (growthProgress != 3 && growthProgress != -1
                    && MainApp.getMode().isSelected()) {
                waterLevel++;
            }
            updateFarmPlotBorder();
            updateDisplay();
        });
    }

    public Item harvest() {
        int i = plantedSeed.getName().indexOf(' ');
        return new MatureCrop(plantedSeed.getName().substring(0, i), 1);
    }

    public void grow() {
        if (growthProgress != 3 && growthProgress != 0 && waterLevel < 2 || waterLevel >= 4) {
            growthProgress = -1;
        }
        if (growthProgress < 3 && growthProgress > 0) {
            growthProgress++;
        }
        waterLevel = 0;
    }

    public void kill() {
        if (growthProgress != -1 && growthProgress != 3) {
            growthProgress = -1;
            waterLevel = 0;
        }
    }

}