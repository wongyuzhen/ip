package jane;

/**
 * Enumeration of the different types of tasks Jane supports.
 *
 * <ul>
 *     <li>{@link #TODO} – a basic task with only a description.</li>
 *     <li>{@link #DEADLINE} – a task with a description and a due date/time.</li>
 *     <li>{@link #EVENT} – a task with a description and start/end date-times.</li>
 * </ul>
 */
public enum TaskType {
    TODO,
    DEADLINE,
    EVENT
}
