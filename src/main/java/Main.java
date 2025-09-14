import java.io.IOException;

import jane.JaneGui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Jane using FXML.
 */
public class Main extends Application {

    private JaneGui jane = new JaneGui("./data/tasks.txt");

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
