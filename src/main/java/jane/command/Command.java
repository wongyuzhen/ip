package jane.command;

import jane.JaneException;
import jane.Storage;
import jane.TaskList;
import jane.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException;

    public boolean isExit() {
        return false;
    }
}