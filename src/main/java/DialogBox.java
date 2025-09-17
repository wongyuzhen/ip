import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A reusable chat bubble component consisting of a text label and an avatar image.
 *
 * <p>Backed by {@code DialogBox.fxml}. Provides factory methods to construct
 * a "user" bubble (image on the right) and a "Jane" bubble (image on the left).</p>
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a dialog box and loads its FXML template.
     *
     * @param text the message text to display
     * @param img  the avatar image to show alongside the text
     * @throws IllegalStateException if the FXML cannot be loaded
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load FXML for DialogBox.", e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the order of child nodes so that the avatar appears on the left and the text on the right.
     *
     * <p>Also aligns the box to the top-left and adds a CSS class to style replies differently.</p>
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a dialog bubble styled for the user (avatar on the right).
     *
     * @param text the user message
     * @param img  the user's avatar image
     * @return a new {@code DialogBox} instance for user messages
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog bubble styled for Jane (avatar on the left).
     *
     * @param text Jane's message
     * @param img  Jane's avatar image
     * @return a new {@code DialogBox} instance for Jane replies
     */
    public static DialogBox getJaneDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
