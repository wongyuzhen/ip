package jane;

import jane.command.Command;

public class JaneGui {
    private final Storage storage;
    private final TaskList tasks;
    private final GuiUi ui;

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

    /** Run the same welcome message your CLI prints, but return it for GUI. */
    public String getWelcome() {
        ui.clear();
        ui.showWelcome();
        return ui.output();
    }

    /** Parse & execute a command; return exactly what Ui would have printed. */
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
