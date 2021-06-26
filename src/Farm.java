/**
 *  This represents the farm.
 *
 *  @author Ali.bab.la
 *  @version 1.0
 */
public class Farm {
    private FarmPlot[] plots;
    private final int startingSize = 16;

    /** Constructor for a new farm. Initializes seeds and plots.*/
    public Farm() {
        plots = new FarmPlot[startingSize];
        for (int i = 0; i < plots.length; i++) {
            plots[i] = new FarmPlot(new Seed(1), (int) ((Math.random() * 3) + 1));
        }
    }

    /**
     * Gets a specific plot from the farm.
     *
     * @param plotNumber the number of the plot
     * @return the plot with the specified number
     */
    public FarmPlot getPlot(int plotNumber) {
        return plots[plotNumber];
    }

    /**
     * Gets the number of active farm plots.
     *
     * @return the number of active farm plots
     */
    public int getNumberOfPlots() {
        return plots.length;
    }

    /**
     * Gets the array of the farm's plots.
     *
     * @return the array of the farm's plots
     */
    public FarmPlot[] getPlotArray() {
        return plots;
    }

}