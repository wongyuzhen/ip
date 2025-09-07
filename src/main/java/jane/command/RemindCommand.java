package jane.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jane.JaneException;
import jane.Storage;
import jane.TaskList;
import jane.Ui;


public class RemindCommand extends Command {
    private final int weeks; // >= 1

    public RemindCommand(int weeks) {
        this.weeks = Math.max(weeks, 1);
    }

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
