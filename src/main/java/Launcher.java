import javafx.application.Application;

/**
 * Standalone launcher to avoid JavaFX classpath/module issues when starting the app.
 *
 * <p>Delegates to {@link Application#launch(Class, String...)} with {@link Main}.</p>
 */
public class Launcher {
    /**
     * Classic {@code public static void main} entry point that bootstraps JavaFX and launches the app.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
