package application;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    private Button rock, paper, scissors, playAgain;
    private Scene play, displayResult;
    private Text welcomeMessage, rulesMessage, startMessage, resultMessage;
    private int userSelection;
    private int compSelection;
    private String result;
    private String player1Pick;
    private String computerPick;
    private Pane confettiPane;
    private Random random;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Rock-Paper-Scissors");

        random = new Random();

        // Text messages for the main scene
        welcomeMessage = new Text("Welcome to Rock Paper Scissors!");
        rulesMessage = new Text("Remember that Rock beats Scissors, Paper beats Rock, and Scissors beats Paper.");
        startMessage = new Text("Please make your selection below:");
        
        // Bold result message text
        resultMessage = new Text();
        resultMessage.setFont(Font.font("Arial", FontWeight.BOLD, 17)); // Set result text to bold

        // Assign images to variables with error-checking
        Image rockImage = new Image("file:/C:/Users/steff/Desktop/1000_F_243068956_Gk65xafr8n50DcuEFvdf0GPCrbN28ICE.jpg");
        Image paperImage = new Image("file:/C:/Users/steff/Desktop/1000_F_243068964_BqrpjOdMd2U7ZEGLjiO8l7h4Ut1j9hcA.jpg");
        Image scissorsImage = new Image("file:/C:/Users/steff/Desktop/scissors-gesture-on-left-hand-for-concept-of-rock-paper-scissors-game-isolated-on-white-background-free-photo.jpeg");

        // Set up ImageView with fixed size for each button
        ImageView rockView = new ImageView(rockImage);
        rockView.setFitWidth(100);
        rockView.setFitHeight(100);

        ImageView paperView = new ImageView(paperImage);
        paperView.setFitWidth(100);
        paperView.setFitHeight(100);

        ImageView scissorsView = new ImageView(scissorsImage);
        scissorsView.setFitWidth(100);
        scissorsView.setFitHeight(100);

        // Set up the buttons with images
        rock = new Button("Rock", rockView);
        paper = new Button("Paper", paperView);
        scissors = new Button("Scissors", scissorsView);

        // Set up VBox with messages
        VBox vbox = new VBox(10, welcomeMessage, rulesMessage, startMessage);
        vbox.setAlignment(Pos.CENTER);

        // Set up HBox with buttons and add spacing
        HBox hbox1 = new HBox(20, rock, paper, scissors);
        hbox1.setAlignment(Pos.CENTER);
        hbox1.setPadding(new Insets(10));

        // Main VBox layout to contain vbox and hbox1
        VBox main = new VBox(20, vbox, hbox1);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(15));

        // BorderPane layout for main scene
        BorderPane border = new BorderPane();
        border.setCenter(main);

        // Display results scene setup with a background pane for confetti effect
        playAgain = new Button("Play Again");
        confettiPane = new Pane();
        VBox results = new VBox(10, resultMessage, playAgain);
        results.setAlignment(Pos.CENTER);
        BorderPane border1 = new BorderPane(confettiPane);
        border1.setCenter(results);

        // Set both scenes to equal sizes
        play = new Scene(border, 600, 400);
        displayResult = new Scene(border1, 600, 400);

        // Button actions for user selections
        rock.setOnAction(e -> handleUserSelection(1, "Rock", primaryStage));
        paper.setOnAction(e -> handleUserSelection(2, "Paper", primaryStage));
        scissors.setOnAction(e -> handleUserSelection(3, "Scissors", primaryStage));

        // Play again button action to return to main scene
        playAgain.setOnAction(e -> primaryStage.setScene(play));

        // Set the initial scene and display the GUI
        primaryStage.setScene(play);
        primaryStage.show();

        // Set up "Enter" key handler to reset the scene and clear confetti
        displayResult.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                stopConfetti();
                primaryStage.setScene(play);
            }
        });
    }

    private void handleUserSelection(int selection, String pick, Stage primaryStage) {
        userSelection = selection;
        player1Pick = pick;
        displayResults(primaryStage);
        changeBackgroundColor(displayResult);  // Update background color with each selection
    }

    private void changeBackgroundColor(Scene scene) {
        // Change background color to a random color
        Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        scene.setFill(color);  // Apply the color to the scene’s background
    }

    // Generate a random selection for the computer
    public int computerSelection() {
        compSelection = random.nextInt(3) + 1;

        if (compSelection == 1) {
            computerPick = "Rock";
        } else if (compSelection == 2) {
            computerPick = "Paper";
        } else {
            computerPick = "Scissors";
        }
        return compSelection;
    }

    // Apply the game logic
    public String runGame() {
        computerSelection();
        if ((userSelection == 1 && compSelection == 3) ||
            (userSelection == 2 && compSelection == 1) ||
            (userSelection == 3 && compSelection == 2)) {
            result = "Congratulations. You win!!";
            showWinningAlertAndConfetti();
        } else if ((userSelection == 1 && compSelection == 2) ||
                   (userSelection == 2 && compSelection == 3) ||
                   (userSelection == 3 && compSelection == 1)) {
            result = "The computer wins. Better luck next time!";
        } else {
            result = "The game was a tie! Please try again.";
        }
        return result;
    }

    // Display the outcome of the game with animation
    public void displayResults(Stage primaryStage) {
        runGame();
        resultMessage.setText("You picked " + player1Pick + " and the computer picked " + computerPick + ". " + result);

        // Add Fade and Scale Animations for result message
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), resultMessage);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.5), resultMessage);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.play();

        primaryStage.setScene(displayResult);
        changeBackgroundColor(displayResult);  // Set a new background color each time results are displayed
    }

    // Method to show winning alert and confetti effect
    private void showWinningAlertAndConfetti() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Victory!");
        alert.setHeaderText("Congratulations, You Won!");
        alert.setContentText("Great job on winning! Would you like to play again?");
        alert.showAndWait();

        createConfetti();
    }

    // Method to create confetti effect
    private void createConfetti() {
        for (int i = 0; i < 50; i++) {
            Circle confetti = new Circle(5, Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            confetti.setLayoutX(300);
            confetti.setLayoutY(200);

            TranslateTransition translate = new TranslateTransition(Duration.seconds(2), confetti);
            translate.setByX((random.nextInt(400) - 200));
            translate.setByY((random.nextInt(400) - 200));
            translate.setCycleCount(1);

            confettiPane.getChildren().add(confetti);
            translate.play();
        }
    }

    // Method to stop confetti effect
    private void stopConfetti() {
        confettiPane.getChildren().clear();
    }
}
