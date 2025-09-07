import jane.JaneGui;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private JaneGui jane;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image janeImage = new Image(this.getClass().getResourceAsStream("/images/DaJane.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Jane instance */
    public void setJane(JaneGui j) {
        assert j != null : "Injected JaneGui must not be null";
        jane = j;

        String greeting = jane.getWelcome();
        assert greeting != null && !greeting.isEmpty() : "Greeting should not be empty";

        dialogContainer.getChildren().add(
                DialogBox.getJaneDialog(greeting, janeImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Jane's reply
     * and then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        assert jane != null : "JaneGui must be injected before handling input";

        String input = userInput.getText();
        assert input != null : "TextField should not return null";

        String response = jane.getResponse(input);
        assert response != null : "Jane response must not be null";

        boolean shouldExit = response.startsWith("[EXIT]");
        if (shouldExit) {
            response = response.substring(6); // strip [EXIT]
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getJaneDialog(response, janeImage)
        );
        userInput.clear();

        if (shouldExit) {
            // Close after 5 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> {
                Stage stage = (Stage) scrollPane.getScene().getWindow();
                stage.close();
            });
            delay.play();
        }
    }
}
