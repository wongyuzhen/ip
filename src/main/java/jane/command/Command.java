package jane.command;

import jane.JaneException;
import jane.Storage;
import jane.TaskList;
import jane.Ui;

/**
 * Abstract base class for all user commands in Jane.
 *
 * <p>Each {@code Command} encapsulates a user instruction,
 * and defines how it should act on the {@link TaskList},
 * communicate results via {@link Ui}, and update {@link Storage}.</p>
 */
public abstract class Command {
    /**
     * Executes the command.
     *
     * @param tasks   the current task list to modify or query
     * @param ui      the user interface for displaying output
     * @param storage the storage handler for saving/loading tasks
     * @throws JaneException if an error occurs during execution
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException;

    /**
     * Indicates whether this command should terminate the program.
     *
     * @return {@code true} if the command exits Jane, otherwise {@code false}
     */
    public boolean isExit() {
        return false;
    }
}
