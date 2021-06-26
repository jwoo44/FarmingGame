import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *  This is the main application.
 *
 *  @author Ali.bab.la
 *  @version 1.0
 */
public class MainApp extends Application {

    private Scene titleScene;
    private Scene configScene;
    private Scene mainGameScene;
    private Scene marketScene;
    private Scene inventoryScene;
    private Player player = new Player();
    private Game game = new Game();
    private Farm farm = new Farm();
    private Market market = new Market(game);
    private static Inventory inventory = new Inventory();
    private Date date = new Date();
    private Text currMoney = new Text(player.getMoneyToString());
    private static ComboBox<Seed> toPlant = new ComboBox<>();
    private static ToggleButton mode = new ToggleButton("Water");
    private Text currMoneyMarket = new Text(player.getMoneyToString());
    private VBox marketSeedDisplay = new VBox();
    private ComboBox<Seed> marketBuyBox = new ComboBox<>();
    private ComboBox<Item> marketSellBox = new ComboBox<>();
    private Text name = new Text(player.getName());
    private Text newDay = new Text("Tip: water plants until they're green each day.");

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start/quit screen
     * @param primaryStage window
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);

        // Layout 1: Title Screen -----------------------------
        VBox titlePane = new VBox();
        titlePane.setSpacing(20);
        titlePane.setAlignment(Pos.CENTER);
        titlePane.setStyle("-fx-background-color: lavender;");
        titleScene = new Scene(titlePane, 500, 500);

        try {
            Image image = new Image(new FileInputStream("res/img/title.png"));
            ImageView view = new ImageView(image);
            view.setPreserveRatio(true);
            view.setFitHeight(250);
            titlePane.getChildren().add(view);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Text name = new Text("Farming Game");
        name.setFont(Font.font("Impact", 50));

        Button start = new Button("Start Game");
        Button quit = new Button("Quit");

        start.setOnAction(e -> primaryStage.setScene(configScene));

        quit.setOnAction(e -> primaryStage.close());

        titlePane.getChildren().addAll(name, start, quit);
        // Layout 1: Title Screen END -------------------------


        // Layout 2: Config Screen ----------------------------
        configScreen(primaryStage);
        // Layout 2: Config Screen END ------------------------

        // Layout 3: Main Game Screen -------------------------
        mainGameScreen(primaryStage);
        // Layout 3: Main Game Screen END ---------------------

        // Layout 4: Market Screen  ---------------------------
        marketScreen(primaryStage);
        // Layout 4: Market Screen END ------------------------

        // Layout 5: Inventory Screen -------------------------
        inventoryScreen(primaryStage);
        // Layout 5: Inventory Screen END ---------------------

        // Stage setup
        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("res/img/icon.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(titleScene);
        primaryStage.setTitle("Farming Game");
        primaryStage.show();
    }

    /**
     * Configuration screen
     * @param primaryStage window
     */
    public void configScreen(Stage primaryStage) {
        VBox configPane = new VBox();
        configPane.setSpacing(10);
        configPane.setPadding(new Insets(10, 10, 10, 10));
        configPane.setStyle("-fx-background-color: lavender;");
        configScene = new Scene(configPane, 500, 500);

        Text textName = new Text("Enter your name:");
        textName.setFont(Font.font("Comic Sans MS", 20));
        Text setDifficulty = new Text("Choose your difficulty:");
        setDifficulty.setFont(Font.font("Comic Sans MS", 20));
        Text chooseSeed = new Text("Choose your farm seed:");
        chooseSeed.setFont(Font.font("Comic Sans MS", 20));

        ToggleGroup groupDiff = new ToggleGroup();
        RadioButton diffButton1 = new RadioButton("Easy");
        diffButton1.setToggleGroup(groupDiff);
        diffButton1.setSelected(true);
        RadioButton diffButton2 = new RadioButton("Normal");
        diffButton2.setToggleGroup(groupDiff);
        RadioButton diffButton3 = new RadioButton("Hard");
        diffButton3.setToggleGroup(groupDiff);

        TextField inputName = new TextField();
        Button play = new Button("Play Game");
        Text invalid = new Text("Please enter a name.");

        ToggleGroup groupSeed = new ToggleGroup();
        RadioButton seed1 = new RadioButton("Wheat seed");
        seed1.setToggleGroup(groupSeed);
        seed1.setSelected(true);
        RadioButton seed2 = new RadioButton("Rice seed");
        seed2.setToggleGroup(groupSeed);
        RadioButton seed3 = new RadioButton("Potato seed");
        seed3.setToggleGroup(groupSeed);
        RadioButton seed4 = new RadioButton("Barley seed");
        seed4.setToggleGroup(groupSeed);

        configPane.getChildren().addAll(textName, inputName, play, setDifficulty, diffButton1,
                diffButton2, diffButton3, chooseSeed, seed1, seed2, seed3, seed4);

        play.setOnAction(e -> {
            if (inputName.getText().trim().length() == 0) {
                invalid.setFill(Color.RED);
                if (!configPane.getChildren().contains(invalid)) {
                    configPane.getChildren().add(invalid);
                }
            } else {
                game.setDifficulty(((RadioButton) groupDiff.getSelectedToggle()).getText());
                if (game.getDifficulty() == 0) {
                    player.gainMoney(1000.00);
                } else if (game.getDifficulty() == 1) {
                    player.gainMoney(500.00);
                } else if (game.getDifficulty() == 2) {
                    player.gainMoney(250.00);
                }
                player.setName(inputName.getText().trim());
                name.setText("Name: " + player.getName());
                name.setFont(Font.font("Comic Sans MS", 18));
                inventory.addItem(new Seed(((RadioButton) groupSeed.getSelectedToggle()).getText(),
                        10));
                update();
                primaryStage.setScene(mainGameScene);
            }
        });
    }

    /**
     * Main game screen
     * @param primaryStage window
     */
    public void mainGameScreen(Stage primaryStage) {
        VBox mainGamePane = new VBox();
        mainGameScene = new Scene(mainGamePane, 500, 500);
        mainGamePane.setPadding(new Insets(10, 10, 10, 10));
        mainGamePane.setSpacing(10);
        mainGamePane.setStyle("-fx-background-color: lavender;");

        // Name display
        HBox centerName = new HBox();
        centerName.setAlignment(Pos.CENTER);
        centerName.getChildren().add(name);
        mainGamePane.getChildren().add(centerName);
        // Name display END

        // Calendar portion
        HBox calendar = new HBox();
        mainGamePane.getChildren().add(calendar);
        calendar.setSpacing(80);

        Text calendarDate = new Text(date.calendarDate());
        Text whichSeason = new Text(date.getSeason());

        HBox bedDay = new HBox();
        mainGamePane.getChildren().add(bedDay);
        bedDay.setSpacing(10);
        bedDay.setAlignment(Pos.CENTER_LEFT);
        Button goBed = new Button("Go to Bed");

        goBed.setOnAction(e -> {
            date.goBed();
            calendarDate.setText(date.calendarDate());
            whichSeason.setText(date.getSeason());
            ArrayList<Seed> seeds = market.getSeeds();
            for (Seed s : seeds) {
                s.setQuantity((int) ((Math.random() * 7) + 7));
            }
            for (FarmPlot f : farm.getPlotArray()) {
                f.grow();
            }
            randomEvent();
            update();
        });

        bedDay.getChildren().addAll(goBed, newDay);
        calendar.getChildren().addAll(calendarDate, whichSeason);
        // Calendar portion END

        // Money display
        calendar.getChildren().add(currMoney);
        // Money display end

        // Plot grid
        HBox plotAlign = new HBox();
        VBox modeAlign = new VBox();
        Text modeLabel = new Text("Watering mode:");
        Text plantLabel = new Text("Select seed to plant:");
        modeAlign.setSpacing(15.0);
        plotAlign.setSpacing(20);
        Text plotText = new Text("Farm Plots:");
        plotText.setFont(Font.font("Comic Sans MS", 20));
        GridPane plots = new GridPane();
        plots.setHgap(30);
        plots.setVgap(30);
        int num = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                plots.add(farm.getPlot(num).getDisplay(), r, c);
                num++;
            }
        }
        modeAlign.getChildren().addAll(plantLabel, toPlant, modeLabel, mode);
        plotAlign.getChildren().addAll(plots, modeAlign);
        mainGamePane.getChildren().addAll(plotText, plotAlign);
        // Plot grid END

        Button inventoryButton = new Button("View Inventory");
        inventoryButton.setOnAction(e -> {
            primaryStage.setScene(inventoryScene);
            inventory.display();
        });
        Button goMarket = new Button("Go to Market");
        goMarket.setOnAction(e -> {
            primaryStage.setScene(marketScene);
            marketUpdate();
        });
        mainGamePane.getChildren().addAll(inventoryButton, goMarket);
    }

    public void inventoryScreen(Stage primaryStage) {
        BorderPane border = new BorderPane();
        border.setStyle("-fx-background-color: lavender;");
        border.setPadding(new Insets(10, 10, 10, 10));
        Button returnButton = new Button("Go back");
        returnButton.setOnAction(e -> primaryStage.setScene(mainGameScene));
        HBox btnBox = new HBox();
        Text label = new Text("Inventory");
        label.setFont(Font.font("Comic Sans MS", 20));
        border.setTop(label);
        btnBox.getChildren().add(returnButton);
        btnBox.setAlignment(Pos.CENTER);
        border.setBottom(btnBox);
        border.setCenter(inventory.getInventoryBox());
        inventoryScene = new Scene(border, 500, 500);
    }

    public void marketScreen(Stage primaryStage) {
        VBox marketPane = new VBox();
        marketScene = new Scene(marketPane, 500, 500);
        marketPane.setPadding(new Insets(10, 10, 10, 10));
        marketPane.setSpacing(10);
        marketPane.setStyle("-fx-background-color: lavender;");

        Text errorMessage = new Text();

        // Info display portion
        HBox marketBar = new HBox();
        marketPane.getChildren().add(marketBar);
        marketBar.setSpacing(85);

        Button exitMarketButton = new Button("Return to farm");

        exitMarketButton.setOnAction(e -> {
            update();
            primaryStage.setScene(mainGameScene);
        });

        marketBar.getChildren().addAll(exitMarketButton, currMoneyMarket);
        // Info display END

        // Set up columns
        HBox marketBox = new HBox();
        marketPane.getChildren().add(marketBox);
        marketBox.setSpacing(50);
        VBox marketLeftColumn = new VBox();
        marketLeftColumn.setSpacing(10);
        VBox marketRightColumn = new VBox();
        marketRightColumn.setSpacing(10);
        marketBox.getChildren().addAll(marketLeftColumn, marketRightColumn);

        // Buy display
        HBox marketBuy = new HBox();
        marketLeftColumn.getChildren().add(marketBuy);
        marketBuy.setSpacing(20);

        Button marketBuyButton = new Button("Buy one");
        marketBuyButton.setOnAction(e -> {
            if (!inventory.isFull()) {
                Seed chosenSeed = marketBuyBox.getValue();
                if (chosenSeed != null && player.getMoney() >= market.getMarketPrice(chosenSeed)) {
                    player.spendMoney(market.getMarketPrice(chosenSeed));
                    inventory.addItem(chosenSeed.removeOneSeed());
                    marketUpdate();
                }
            } else {
                errorMessage.setText("Your inventory is full!");
                errorMessage.setFill(Color.RED);
            }
        });
        marketBuy.getChildren().addAll(marketBuyBox, marketBuyButton);

        // Sell display
        HBox marketSell = new HBox();
        marketRightColumn.getChildren().add(marketSell);
        marketSell.setSpacing(20);

        Button marketSellButton = new Button("Sell one");
        marketSellButton.setOnAction(e -> {
            Item chosenItem = marketSellBox.getValue();
            if (chosenItem instanceof Seed) {
                player.gainMoney(market.getBuyPrice());
                market.addSeed(((Seed) chosenItem).removeOneSeed());
                marketUpdate();
            } else if (chosenItem instanceof MatureCrop) {
                player.gainMoney(market.getCropBuyPrice());
                ((MatureCrop) chosenItem).removeOneCrop();
                marketUpdate();
            }
            errorMessage.setText("");
        });
        marketSell.getChildren().addAll(marketSellBox, marketSellButton);
        // Sell display END

        // Seed display
        Text marketSeedText = new Text("For Sale:");
        marketSeedText.setFont(Font.font("Comic Sans MS", 20));
        marketLeftColumn.getChildren().add(marketSeedText);
        marketLeftColumn.getChildren().add(marketSeedDisplay);
        marketLeftColumn.getChildren().add(errorMessage);
        // Seed display END

        // Buy display END

        // initialize sell box
        marketUpdate();
    }

    /**
     * Updates any display text that change upon an event occurring
     */
    private void update() {
        currMoneyMarket.setText(player.getMoneyToString());
        currMoney.setText(player.getMoneyToString());
        FarmPlot[] plotArray = farm.getPlotArray();
        for (FarmPlot p : plotArray) {
            p.updateDisplay();
        }

        updateToPlant();
    }

    public static void updateToPlant() {
        int oldToPlantSelection = toPlant.getSelectionModel().getSelectedIndex();
        int oldToPlantSize = toPlant.getItems().size();
        toPlant.getItems().clear();
        for (Item i : inventory.getInventory()) {
            if (i instanceof Seed && !i.isOut()) {
                toPlant.getItems().add((Seed) i);
            }
        }
        if (toPlant.getItems().size() == oldToPlantSize) {
            toPlant.getSelectionModel().select(oldToPlantSelection);
        }
    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static ComboBox<Seed> getToPlant() {
        return toPlant;
    }

    public static ToggleButton getMode() {
        return mode;
    }

    private void marketUpdate() {
        currMoneyMarket.setText(player.getMoneyToString());
        ArrayList<Seed> marketSeeds = market.getSeeds();
        marketSeedDisplay.getChildren().clear();

        int oldBuySelection = marketBuyBox.getSelectionModel().getSelectedIndex();
        int oldBuySize = marketBuyBox.getItems().size();
        marketBuyBox.getItems().clear();
        for (Seed s : marketSeeds) {
            if (!s.outOfSeed()) {
                s.updateDisplay();
                marketSeedDisplay.getChildren().add(s.getDisplay());
                String priceString = String.format("%.2f", market.getMarketPrice(s));
                Text t = new Text("(" + s.getName() + " price: $" + priceString + ")");
                marketSeedDisplay.getChildren().add(t);
                marketBuyBox.getItems().add(s);
            }
        }
        if (marketBuyBox.getItems().size() == oldBuySize) {
            marketBuyBox.getSelectionModel().select(oldBuySelection);
        }

        int oldSellSelection = marketSellBox.getSelectionModel().getSelectedIndex();
        int oldSellSize = marketSellBox.getItems().size();
        marketSellBox.getItems().clear();
        ArrayList<Item> inventoryItems = inventory.getInventory();
        for (Item i : inventoryItems) {
            if (!i.isOut()) {
                i.updateDisplay();
                marketSellBox.getItems().add(i);
            }
        }
        if (marketSellBox.getItems().size() == oldSellSize) {
            marketSellBox.getSelectionModel().select(oldSellSelection);
        }
    }

    private void randomEvent() {
        //Four outcomes: no event, bills, blight, shortage
        //Probability of any event: 5% easy, 10% normal, 15% hard
        //Probabilities are evenly divided among the three events
        if ((int) (Math.random() * 100 + 1) <= 5 * (game.getDifficulty() + 1)) {
            int event = (int) (Math.random() * 4); //0 = bills, 1 = blight, 2 = shortage
            switch (event) {
            case 0:
                //Bills: lose 10% of current money
                double moneySpent = player.getMoney() * 0.1;
                newDay.setText("Uh oh... the bills came in. You paid $"
                        + String.format("%.2f", moneySpent) + ".");
                player.spendMoney(moneySpent);
                break;
            case 1:
                //Blight: lose all of a specific crop type
                Seed blighted = new Seed(1);
                int spaceIndex = blighted.getName().indexOf(' ');
                newDay.setText("Uh oh... all your "
                        + blighted.getName().substring(0, spaceIndex).toLowerCase()
                        + " plants are dead of blight.");
                FarmPlot[] plotArray = farm.getPlotArray();
                for (FarmPlot p : plotArray) {
                    if (p.getSeed() != null && p.getSeed().equals(blighted)) {
                        p.kill();
                    }
                }
                break;
            case 2:
                //Shortage: market has less stock
                newDay.setText("Uh oh... the market's low on seeds today.");
                for (Seed s : market.getSeeds()) {
                    s.setQuantity(s.getQuantity() - 6);
                }
                break;
            case 3:
                newDay.setText("Uh oh... looks like it rained a lot last night. "
                        + "Be careful when watering!");
                FarmPlot[] plotArray2 = farm.getPlotArray();
                for (FarmPlot p : plotArray2) {
                    if (p.getGrowthProgress() > 0 && p.getGrowthProgress() < 3) {
                        p.setWaterLevel((int) (Math.random() * 6));
                    }
                }
                break;
            default:
            }
        } else {
            newDay.setText("Ahh... a bright new day!");
        }
    }
}
