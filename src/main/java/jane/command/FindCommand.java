package jane.command;

import jane.Storage;
import jane.Task;
import jane.TaskList;
import jane.Ui;

/**
 * Command that searches for tasks whose descriptions contain a given keyword.
 */
public class FindCommand extends Command {
    private final String keyword;

    /**
     * Constructs a new {@code FindCommand}.
     *
     * @param keyword the keyword to search for (case-insensitive)
     */
    public FindCommand(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    /**
     * Searches the task list for matching tasks and displays them.
     * If no matches are found, displays an error instead.
     */
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
