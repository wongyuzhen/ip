package jane.command;

import jane.TaskList;
import jane.Ui;
import jane.Storage;
import jane.Task;

public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        TaskList matchingTasks = new TaskList();
        for (Task task : tasks.asArrayList()) {
            if (task.getDescription().toLowerCase().contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            ui.showError("No tasks found matching: " + keyword);
        } else {
            ui.showFoundTasks(matchingTasks);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
