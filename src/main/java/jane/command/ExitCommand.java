package jane.command;

import jane.Storage;
import jane.TaskList;
import jane.Ui;


/**
 * Command that terminates Jane.
 *
 * <p>{@code ExitCommand} displays a farewell message and signals
 * the main loop to exit.</p>
 */
public class ExitCommand extends Command {
    /**
     * Displays the farewell message.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
