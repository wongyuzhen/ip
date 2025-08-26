package jane.command;

import jane.TaskList;
import jane.Ui;
import jane.Storage;

public class ListCommand extends Command {
    @Override public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}