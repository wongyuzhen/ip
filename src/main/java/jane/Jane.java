package jane;

import jane.command.*;

/**
 * Entry point and core logic for the Jane chatbot.
 *
 * <p>Jane is a command-line based chatbot that manages tasks.
 * It loads tasks from storage on startup, interacts with the user via {@link Ui},
 * processes commands parsed by {@link Parser}, and updates the task list and storage
 * accordingly.</p>
 */
public class Jane {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Jane chatbot instance.
     *
     * @param filePath path to the data file for storing tasks
     */
    public Jane(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (JaneException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main event loop of Jane.
     *
     * <p>Displays a welcome message, then repeatedly:
     * <ul>
     *     <li>Reads the next user command.</li>
     *     <li>Parses the command via {@link Parser}.</li>
     *     <li>Executes the command on the current task list and storage.</li>
     *     <li>Displays results or errors via the UI.</li>
     * </ul>
     * The loop exits when the command indicates termination.</p>
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (JaneException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
                System.out.println();
            }
        }
    }

    /**
     * Main method that launches the chatbot with the default data file path.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Jane("./data/tasks.txt").run();
    }
}
