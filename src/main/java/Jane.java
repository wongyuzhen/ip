public class Jane {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    public static void main(String[] args) {
        new Jane("./data/tasks.txt").run();
    }

}