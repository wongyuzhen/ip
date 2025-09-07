package jane;

import java.util.Scanner;

/**
 * Represents the user interface for interacting with the task manager.
 * Provides methods for displaying messages to the user, reading commands, and showing task-related information.
 *
 * <p>Methods include displaying welcome and farewell messages, showing task addition, removal, and completion status,
 * and handling user input for commands.</p>
 *
 * @author Wong Yu Zhen
 */
public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a new instance of the Ui class.
     */
    public Ui() {}

    /**
     * Displays a friendly welcome message when the app starts.
     */
    public void showWelcome() {
        System.out.println("____________________________________________________________\n"
                + "Hello! I'm Jane\n"
                + "What can I do for you?\n"
                + "____________________________________________________________\n");
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return The user's input as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints a separator line to visually separate sections in the output.
     * Helps keep the display tidy and easy to read.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    /**
     * Displays an error message when something goes wrong.
     * This method adds a bit of empathy and makes it clear that something's wrong.
     *
     * @param msg The error message to display.
     */
    public void showError(String msg) {
        System.out.println("Oh no! " + msg);
    }

    /**
     * Bids farewell to the user when they decide to exit.
     */
    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Shows the user that a task has been successfully added to their list.
     * It gives feedback on the new task and shows the updated task count.
     *
     * @param t The task that was added.
     * @param size The current number of tasks in the list.
     */
    public void showAdded(Task t, int size) {
        System.out.println("Got it. I've added this task:\n" + t
                + "\nNow you have " + size + " tasks in the list.");
    }

    /**
     * Informs the user that a task has been removed from the list.
     * The message shows the task that was removed and the updated task count.
     *
     * @param t The task that was removed.
     * @param size The current number of tasks after removal.
     */
    public void showRemoved(Task t, int size) {
        System.out.println("Noted. I've removed this task:\n" + t
                + "\nNow you have " + size + " tasks in the list.");
    }

    /**
     * Notifies the user that a task has been marked as done or undone.
     * This method adapts the message based on whether the task was completed or not.
     *
     * @param t The task that was marked.
     * @param done The status of the task (true if done, false if undone).
     */
    public void showMarked(Task t, boolean done) {
        System.out.println((done ? "Nice! I've marked this task as done:\n"
                : "OK, I've marked this task as not done yet:\n") + t);
    }

    /**
     * Displays the entire list of tasks for the user and their current statuses.
     *
     * @param tasks The list of tasks to display.
     */
    public void showList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    /**
     * Displays the matching tasks found based on the search keyword.
     *
     * @param tasks The list of tasks that match the search keyword.
     */
    public void showFoundTasks(TaskList tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showReminders(TaskList tasks, java.time.LocalDateTime from, java.time.LocalDateTime to,
                              java.time.format.DateTimeFormatter fmt) {
        System.out.println("Upcoming tasks between " + from.format(fmt) + " and " + to.format(fmt) + ":");
        if (tasks.size() == 0) {
            System.out.println("None.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

}
