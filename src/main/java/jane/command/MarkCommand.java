package jane.command;

import jane.JaneException;
import jane.Storage;
import jane.Task;
import jane.TaskList;
import jane.Ui;

public class MarkCommand extends Command {
    private final int index;
    private final boolean done;

    public MarkCommand(int index, boolean done) {
        this.index = index;
        this.done = done;
    }

    @Override public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        if (index < 0 || index >= tasks.size()) {
            throw new JaneException("Task number " + (index + 1) + " does not exist.");
        }
        Task t = tasks.mark(index, done);
        storage.save(tasks.asArrayList());
        ui.showMarked(t, done);
    }
}