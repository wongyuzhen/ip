import java.util.HashMap;
import java.util.Scanner;

public class Jane {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String greeting = "____________________________________________________________\n" +
                "Hello! I'm Jane\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n";
        System.out.println(greeting);

        int count = 0;
        Task[] list = new Task[100];

        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            String[] words = input.split("\\s+"); // split input line into its separate words

            if (input.equals("bye")) {
                String bye = "____________________________________________________________\n" +
                        "Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n";
                System.out.println(bye);
                break;
            }

            if (input.equals("list")) {
                System.out.println("____________________________________________________________\n"
                    + "Here are the tasks in your list:");
                for (int i = 0; i < 100; i++) {
                    if (list[i] != null) {
                        System.out.println((i + 1) + ". " + list[i].toString());
                    }
                }
                System.out.println("____________________________________________________________\n");
            } else if (words[0].equals("mark")) {
                int index = Integer.parseInt(words[1]) - 1;
                list[index].markAsDone();
                System.out.println("____________________________________________________________\n" +
                    "Nice! I've marked this task as done:");
                String taskLine = "[" + list[index].getStatusIcon() + "] " + list[index].getDescription();
                System.out.println(taskLine);
                System.out.println("____________________________________________________________\n");
            } else if (words[0].equals("unmark")) {
                int index = Integer.parseInt(words[1]) - 1;
                list[index].markAsUndone();
                System.out.println("____________________________________________________________\n" +
                        "OK, I've marked this task as not done yet:");
                String taskLine = "[" + list[index].getStatusIcon() + "] " + list[index].getDescription();
                System.out.println(taskLine);
                System.out.println("____________________________________________________________\n");
            } else {
                String[] inputArgs = input.split("\\s?/");
                String[] mainCmd = inputArgs[0].split("\\s+", 2);
                HashMap<String, String> flags = new HashMap<String, String>();
                for (int i = 1; i < inputArgs.length; ++i) {
                    String[] flagParts = inputArgs[i].split("\\s+", 2);
                    flags.put(flagParts[0], flagParts[1]);
                }
                TaskType type = TaskType.valueOf(mainCmd[0].toUpperCase());
                switch (type) {
                    case TODO:
                        // assert mainCmd.length == 1;
                        list[count] = new Task(mainCmd[1]);
                        break;
                    case DEADLINE:
                        String deadlineDate = "";
                        if (flags.containsKey("by")) {
                            deadlineDate = flags.get("by");
                        }
                        list[count] = new Task(mainCmd[1], deadlineDate);
                        break;
                    case EVENT:
                        String fromTime = "";
                        String toTime = "";
                        if (flags.containsKey("from")) {
                            fromTime = flags.get("from");
                        }
                        if (flags.containsKey("to")) {
                            toTime = flags.get("to");
                        }
                        list[count] = new Task(mainCmd[1], fromTime, toTime);
                        break;
                }

                String text = "____________________________________________________________\n" +
                        "Got it. I've added this task:\n" +
                        list[count].toString() +
                        "\nNow you have " + (count + 1) + " tasks in the list.\n" +
                        "____________________________________________________________\n";
                System.out.println(text);

                count++;
            }
        }

        scanner.close();
    }
}