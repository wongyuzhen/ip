package jane.command;

import jane.TaskList;
import jane.Ui;
import jane.Storage;
import jane.Task;
import jane.DateTimeUtil;
import jane.JaneException;

import java.time.LocalDate;

public class DeadlineCommand extends Command {
    private final String description; private final LocalDate by;

    public DeadlineCommand(String description, String byRaw) {
        this.description = description; this.by = DateTimeUtil.parseDate(byRaw);
    }

    @Override public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        Task t = new Task(description, by);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}