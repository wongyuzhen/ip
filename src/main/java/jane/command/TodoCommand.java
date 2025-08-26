package jane.command;

import jane.TaskList;
import jane.Ui;
import jane.Storage;
import jane.Task;
import jane.JaneException;

public class TodoCommand extends Command {
    private final String description;

    public TodoCommand(String description) {
        this.description = description;
    }

    @Override public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        Task t = new Task(description);
        tasks.add(t);
        storage.save(tasks.asArrayList());
        ui.showAdded(t, tasks.size());
    }
}