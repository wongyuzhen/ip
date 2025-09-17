package jane.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jane.JaneException;
import jane.Storage;
import jane.TaskList;
import jane.Ui;

/**
 * Command that displays reminders for upcoming tasks.
 *
 * <p>{@code RemindCommand} filters tasks that fall within the
 * next {@code N} weeks (defaulting to at least 1 week) starting from now,
 * and asks the UI to display them.</p>
 */
public class RemindCommand extends Command {
    private final int weeks; // >= 1

    /**
     * Constructs a new {@code RemindCommand}.
     *
     * @param weeks number of weeks to look ahead (values less than 1 are clamped to 1)
     */
    public RemindCommand(int weeks) {
        this.weeks = Math.max(weeks, 1);
    }

    /**
     * Finds tasks occurring within the reminder window and displays them.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JaneException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusWeeks(weeks);

        TaskList upcoming = tasks.within(now, end);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        ui.showReminders(upcoming, now, end, fmt);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

