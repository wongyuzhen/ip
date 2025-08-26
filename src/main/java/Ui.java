import java.util.Scanner;

public class Ui {
    private final Scanner scanner = new Scanner(System.in);

    public Ui() {}

    public void showWelcome() {
        System.out.println("____________________________________________________________\n" +
                "Hello! I'm Jane\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String msg) {
        System.out.println("Oh no! " + msg);
    }

    public void showBye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showAdded(Task t, int size) {
        System.out.println("Got it. I've added this task:\n" + t +
                "\nNow you have " + size + " tasks in the list.");
    }

    public void showRemoved(Task t, int size) {
        System.out.println("Noted. I've removed this task:\n" + t +
                "\nNow you have " + size + " tasks in the list.");
    }

    public void showMarked(Task t, boolean done) {
        System.out.println((done ? "Nice! I've marked this task as done:\n" :
                "OK, I've marked this task as not done yet:\n") + t);
    }

    public void showList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }
}
