package jane;

import jane.command.Command;

/**
 * A facade class that adapts Jane for use in a GUI environment.
 *
 * <p>{@code JaneGui} mirrors the behavior of the CLI-based {@link Jane}
 * but captures responses into a {@link GuiUi} buffer so they can be
 * displayed in the GUI instead of the console.</p>
 *
 * <p>Its two main responsibilities are:</p>
 * <ul>
 *     <li>Providing the welcome message for the GUI on startup.</li>
 *     <li>Parsing and executing user input commands, returning
 *     the same output Jane would produce in CLI mode.</li>
 * </ul>
 */
public class JaneGui {
    private final Storage storage;
    private final TaskList tasks;
    private final GuiUi ui;

    /**
     * Constructs a {@code JaneGui} instance with task storage and UI.
     *
     * @param filePath path to the data file used for saving/loading tasks
     */
    public JaneGui(String filePath) {
        ui = new GuiUi();
        storage = new Storage(filePath);
        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (JaneException e) {
            ui.showError(e.getMessage());
            loaded = new TaskList();
        }
        tasks = loaded;
    }

    /**
     * Returns the chatbot’s welcome message, identical to the CLI version.
     *
     * @return welcome text for display in the GUI
     */
    public String getWelcome() {
        ui.clear();
        ui.showWelcome();
        return ui.output();
    }

    /**
     * Parses and executes a command, returning the generated response text.
     *
     * <p>This method delegates parsing to {@link Parser}, then executes the
     * corresponding {@link Command} against the current {@link TaskList}.
     * The {@link GuiUi} captures and formats the output.</p>
     *
     * @param input raw user input string
     * @return the response text Jane would have printed, prefixed with
     *         {@code [EXIT]} if the command ends the session
     */
    public String getResponse(String input) {
        ui.clear();
        boolean isExit = false;
        try {
            Command c = Parser.parse(input);
            c.execute(tasks, ui, storage);
            isExit = c.isExit();
        } catch (JaneException e) {
            ui.showError(e.getMessage());
        }
        // Return both response and exit flag
        return (isExit ? "[EXIT]" : "") + ui.output();
    }
}
