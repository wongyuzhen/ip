package jane;

/**
 * A GUI-specific Ui that captures messages into a string buffer instead of printing to console.
 * Messages are the same as CLI Jane, but without separator lines.
 */
public class GuiUi extends Ui {
    private final StringBuilder buf = new StringBuilder();

    public void clear() {
        buf.setLength(0);
    }
    public String output() {
        return buf.toString().trim();
    }

    private void appendLines(String... lines) {
        for (String line : lines) {
            buf.append(line).append("\n");
        }
    }

    @Override
    public void showWelcome() {
        appendLines("Hello! I'm Jane", "What can I do for you?");
    }

    @Override
    public void showError(String msg) {
        appendLines("Oh no! " + msg);
    }

    @Override
    public void showBye() {
        appendLines("Bye. Hope to see you again soon!");
    }

    @Override
    public void showAdded(Task t, int size) {
        buf.append("Got it. I've added this task:\n")
            .append(t).append("\nNow you have ").append(size)
            .append(" tasks in the list.\n");
    }

    @Override
    public void showRemoved(Task t, int size) {
        buf.append("Noted. I've removed this task:\n")
            .append(t).append("\nNow you have ").append(size)
            .append(" tasks in the list.\n");
    }

    @Override
    public void showMarked(Task t, boolean done) {
        buf.append(done
                    ? "Nice! I've marked this task as done:\n"
                    : "OK, I've marked this task as not done yet:\n")
            .append(t).append("\n");
    }

    @Override
    public void showList(TaskList tasks) {
        buf.append("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            buf.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
    }

    @Override
    public void showFoundTasks(TaskList tasks) {
        buf.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            buf.append(i + 1).append(".").append(tasks.get(i)).append("\n");
        }
    }

    // readCommand() not used in GUI
}
