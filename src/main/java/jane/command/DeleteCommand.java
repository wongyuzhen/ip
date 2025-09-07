package jane.command;

import jane.JaneException;
import jane.Storage;
import jane.Task;
import jane.TaskList;
import jane.Ui;

public class DeleteCommand extends Command {
    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        if (index < 0 || index >= tasks.size()) {
            throw new JaneException("Task number " + (index + 1) + " does not exist.");
        }
        Task removed = tasks.remove(index);
        storage.save(tasks.asArrayList());
        ui.showRemoved(removed, tasks.size());
    }
}