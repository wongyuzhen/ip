import java.util.Objects;

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
 * Controller for the main chat window defined in {@code MainWindow.fxml}.
 *
 * <p>Handles user input, displays dialog bubbles for the user and Jane, and
 * gracefully exits when Jane requests termination.</p>
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
            Objects.requireNonNull(this.getClass().getResourceAsStream("/images/DaJane.jpg"))
    );

    /**
     * Called by the JavaFX runtime after FXML fields are injected.
     *
     * <p>Binds the scroll pane to auto-scroll when new dialog nodes are appended.</p>
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the {@link JaneGui} instance used to service user requests and
     * renders Jane's greeting as the first message.
     *
     * @param j non-null Jane facade
     * @throws AssertionError if {@code j} is {@code null} or the greeting is empty
     */
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
            // Close after 2 seconds
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(event -> {
                Stage stage = (Stage) scrollPane.getScene().getWindow();
                stage.close();
            });
            delay.play();
        }
    }
}
