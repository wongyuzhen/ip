import java.io.IOException;

import jane.JaneGui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * Entry point for the Jane chatbot GUI application (JavaFX).
 *
 * <p>Loads the main window from FXML, wires the {@link JaneGui} backend into the
 * controller, and shows the primary stage.</p>
 */
public class Main extends Application {

    private final JaneGui jane = new JaneGui("./data/tasks.txt");

    /**
     * Initializes and displays the primary JavaFX stage.
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
    @Override
    public void start(Stage stage) {
        // App/window title shows up in screenshots and task switchers.
        stage.setTitle("Jane the Chatbot");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setJane(jane); // inject JaneGui
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
