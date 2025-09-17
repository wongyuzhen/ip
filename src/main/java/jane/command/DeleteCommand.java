package jane.command;

import jane.JaneException;
import jane.Storage;
import jane.Task;
import jane.TaskList;
import jane.Ui;


/**
 * Command that deletes a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a new {@code DeleteCommand}.
     *
     * @param index zero-based index of the task to remove
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Removes the task at the specified index, updates storage, and informs the user.
     *
     * @throws JaneException if the index is invalid
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        if (index < 0 || index >= tasks.size()) {
            throw new JaneException("Task number " + (index + 1) + " does not exist.");
        }
        Task removed = tasks.remove(index);
        storage.save(tasks.asArrayList());
        ui.showRemoved(removed, tasks.size());
    }
}
