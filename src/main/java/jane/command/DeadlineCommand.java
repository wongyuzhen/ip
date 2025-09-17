package jane.command;

import java.time.LocalDate;

import jane.DateTimeUtil;
import jane.JaneException;
import jane.Storage;
import jane.Task;
import jane.TaskList;
import jane.Ui;

/**
 * Command that adds a deadline task to the task list.
 *
 * <p>Deadlines consist of a description and a due date
 * (specified using {@code /by}).</p>
 */
public class DeadlineCommand extends Command {
    private final String description;
    private final LocalDate by;

    /**
     * Constructs a new {@code DeadlineCommand}.
     *
     * @param description description of the task
     * @param byRaw       raw string representing the due date
     */
    public DeadlineCommand(String description, String byRaw) {
        this.description = description;
        this.by = DateTimeUtil.parseDate(byRaw);
    }

    /**
     * Creates and adds a deadline task, saves to storage, and informs the user.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        Task t = new Task(description, by);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}
