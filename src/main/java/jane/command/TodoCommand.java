package jane.command;

import jane.JaneException;
import jane.Storage;
import jane.Task;
import jane.TaskList;
import jane.Ui;


/**
 * Command that adds a simple to-do task to the task list.
 *
 * <p>To-dos are basic tasks with only a description and no time constraints.</p>
 */
public class TodoCommand extends Command {
    private final String description;

    /**
     * Constructs a new {@code TodoCommand}.
     *
     * @param description description of the task
     */
    public TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Creates and adds a to-do task, saves to storage, and informs the user.
     */
    @Override public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        Task t = new Task(description);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}
