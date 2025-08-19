public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;
    protected String deadlineDate;
    protected String fromTime;
    protected String toTime;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.type = TaskType.TODO;
    }

    public Task(String description, String date) {
        this.description = description;
        this.isDone = false;
        this.type = TaskType.DEADLINE;
        this.deadlineDate = date;
    }

    public Task(String description, String from, String to) {
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
                        " (by: " + deadlineDate + ")";
            case EVENT:
                return "[E][" + this.getStatusIcon() + "] " + description +
                        " (from: " + fromTime + " to: " + toTime + ")";
        }
        return "";
    }
}
