package jane.command;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jane.DateTimeUtil;
import jane.JaneException;
import jane.Storage;
import jane.Task;
import jane.TaskList;
import jane.Ui;

/**
 * Command that adds an event task to the task list.
 *
 * <p>Events are defined with a description, a start datetime ({@code /from}),
 * and an end time ({@code /to}). The end time must not be earlier than the start.</p>
 */
public class EventCommand extends Command {
    private final String description;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a new {@code EventCommand}.
     *
     * @param description description of the event
     * @param fromRaw     raw string for start date/time
     * @param toRaw       raw string for end time (on the same date as start)
     * @throws JaneException if the parsed end time is before the start time
     */
    public EventCommand(String description, String fromRaw, String toRaw) throws JaneException {
        LocalDateTime fromDT = DateTimeUtil.parseDateTime(fromRaw);
        LocalTime toOnly = DateTimeUtil.parseTime(toRaw);
        LocalDateTime toDT = LocalDateTime.of(fromDT.toLocalDate(), toOnly);
        if (toDT.isBefore(fromDT)) {
            throw new JaneException("Event /to time cannot be before /from time.");
        }
        this.description = description;
        this.from = fromDT;
        this.to = toDT;
    }

    /**
     * Creates and adds an event task, saves to storage, and informs the user.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        Task t = new Task(description, from, to);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}
