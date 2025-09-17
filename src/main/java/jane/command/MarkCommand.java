package jane.command;

import jane.JaneException;
import jane.Storage;
import jane.Task;
import jane.TaskList;
import jane.Ui;

/**
 * Command that marks or unmarks a task in the task list.
 *
 * <p>The {@code MarkCommand} toggles the completion status of a task
 * based on the provided index and {@code done} flag.</p>
 *
 * <ul>
 *     <li>If {@code done} is {@code true}, the task is marked complete.</li>
 *     <li>If {@code done} is {@code false}, the task is marked incomplete.</li>
 * </ul>
 */
public class MarkCommand extends Command {
    private final int index;
    private final boolean done;

    /**
     * Constructs a new {@code MarkCommand}.
     *
     * @param index zero-based index of the task to update
     * @param done  {@code true} to mark complete, {@code false} to mark incomplete
     */
    public MarkCommand(int index, boolean done) {
        this.index = index;
        this.done = done;
    }

    /**
     * Marks/unmarks the task at the specified index, updates storage,
     * and informs the user through the UI.
     *
     * @param tasks   the current task list
     * @param ui      the user interface for displaying output
     * @param storage the storage handler for saving the updated task list
     * @throws JaneException if the index is invalid or out of range
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        if (index < 0 || index >= tasks.size()) {
            throw new JaneException("Task number " + (index + 1) + " does not exist.");
        }
        Task t = tasks.mark(index, done);
        storage.save(tasks.asArrayList());
        ui.showMarked(t, done);
    }
}
