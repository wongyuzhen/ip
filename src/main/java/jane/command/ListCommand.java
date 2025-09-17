package jane.command;

import jane.Storage;
import jane.TaskList;
import jane.Ui;

/**
 * Command that lists all current tasks.
 *
 * <p>Delegates to {@link Ui#showList(TaskList)} for display.</p>
 */
public class ListCommand extends Command {
    /**
     * Displays the current task list.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks);
    }
}
