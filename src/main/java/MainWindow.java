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

import java.util.Objects;

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

    private final Image userImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaUser.png"))
    );
    private final Image janeImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaJane.png"))
    );

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Jane instance */
    public void setJane(JaneGui j) {
        jane = j;

        String greeting = jane.getWelcome();
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
        String input = userInput.getText();
        String response = jane.getResponse(input);

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
