package jane;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a task with a description, completion status, and type.
 * Tasks can be of type TODO, DEADLINE, or EVENT, and include information such as a due date or event timing.
 * <p>This class allows the task to be marked as done or undone and provides a method to format the task for display.</p>
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    // Deadline: LocalDate only
    protected LocalDate deadlineDate;

    // Event: LocalDateTime for from/to
    protected LocalDateTime fromTime;
    protected LocalDateTime toTime;

    /**
     * Constructs a TODO task with the given description.
     * This task is initially marked as not done and has no deadline or event time.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.type = TaskType.TODO;
    }

    /**
     * Constructs a DEADLINE task with the given description and deadline date.
     * This task is initially marked as not done and has no event times.
     *
     * @param description The description of the task.
     * @param date The deadline date of the task.
     */
    public Task(String description, LocalDate date) {
        this.description = description;
        this.isDone = false;
        this.type = TaskType.DEADLINE;
        this.deadlineDate = date;
    }

    /**
     * Constructs an EVENT task with the given description, start time, and end time.
     * This task is initially marked as not done and has no deadline date.
     *
     * @param description The description of the task.
     * @param from The start time of the event.
     * @param to The end time of the event.
     */
    public Task(String description, LocalDateTime from, LocalDateTime to) {
        this.description = description;
        this.isDone = false;
        this.type = TaskType.EVENT;
        this.fromTime = from;
        this.toTime = to;
    }

    /**
     * Returns a status icon for the task.
     * "X" if the task is done, otherwise a space.
     *
     * @return A string representing the task's completion status.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Returns the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description; // mark done task with X
    }

    /**
     * Marks the task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks the task as undone.
     */
    public void markAsUndone() {
        isDone = false;
    }

    /**
     * Returns a string representation of the task, formatted based on its type.
     * The string includes the status icon, description, and additional information (like date or event times).
     *
     * @return A formatted string representing the task.
     */
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
