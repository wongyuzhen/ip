import java.time.LocalDate;
import java.time.LocalDateTime;

public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    // Deadline: LocalDate only
    protected LocalDate deadlineDate;

    // Event: LocalDateTime for from/to
    protected LocalDateTime fromTime;
    protected LocalDateTime toTime;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.type = TaskType.TODO;
    }

    public Task(String description, LocalDate date) {
        this.description = description;
        this.isDone = false;
        this.type = TaskType.DEADLINE;
        this.deadlineDate = date;
    }

    public Task(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.isDone = false;
        this.type = TaskType.EVENT;
        this.fromTime = from;
        this.toTime = to;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public String getDescription() {
        return description; // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsUndone() {
        isDone = false;
    }

    @Override
    public String toString() {
        switch (type) {
            case TODO:
                return "[T][" + this.getStatusIcon() + "] " + description;
            case DEADLINE:
                return "[D][" + this.getStatusIcon() + "] " + description +
                        " (by: " + DateTimeUtil.formatDate(deadlineDate) + ")";
            case EVENT:
                return "[E][" + this.getStatusIcon() + "] " + description +
                        " (from: " + DateTimeUtil.formatDateTime(fromTime) +
                        " to: " + DateTimeUtil.formatDateTime(toTime)+ ")";
        }
        return "[" + this.getStatusIcon() + "] " + description;
    }
}
