package jane.command;

import jane.TaskList;
import jane.Ui;
import jane.Storage;
import jane.Task;
import jane.DateTimeUtil;
import jane.JaneException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventCommand extends Command {
    private final String description; private final LocalDateTime from; private final LocalDateTime to;

    public EventCommand(String description, String fromRaw, String toRaw) throws JaneException {
        LocalDateTime fromDT = DateTimeUtil.parseDateTime(fromRaw);
        LocalTime toOnly = DateTimeUtil.parseTime(toRaw);
        LocalDateTime toDT = LocalDateTime.of(fromDT.toLocalDate(), toOnly);
        if (toDT.isBefore(fromDT)) throw new JaneException("Event /to time cannot be before /from time.");
        this.description = description; this.from = fromDT; this.to = toDT;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        Task t = new Task(description, from, to);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}