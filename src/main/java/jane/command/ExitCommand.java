package jane.command;

import jane.TaskList;
import jane.Ui;
import jane.Storage;

public class ExitCommand extends Command {
    @Override public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    @Override public boolean isExit() {
        return true;
    }
}