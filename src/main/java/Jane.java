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

            try {
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
                } else if (words[0].equals("mark") || words[0].equals("unmark")) {
                    if (words.length < 2) {
                        throw new JaneException("Please specify a task number.");
                    }

                    int index;
                    try {
                        index = Integer.parseInt(words[1]) - 1;
                    } catch (NumberFormatException e) {
                        throw new JaneException("Invalid task number: " + words[1]);
                    }
                    if (index < 0 || index >= count || list[index] == null) {
                        throw new JaneException("Task number " + (index + 1) + " does not exist.");
                    }

                    if (words[0].equals("mark")) {
                        list[index].markAsDone();
                        System.out.println("____________________________________________________________\n" +
                                "Nice! I've marked this task as done:");
                        String taskLine = "[" + list[index].getStatusIcon() + "] " + list[index].getDescription();
                        System.out.println(taskLine);
                        System.out.println("____________________________________________________________\n");
                    } else {
                        list[index].markAsUndone();
                        System.out.println("____________________________________________________________\n" +
                                "OK, I've marked this task as not done yet:");
                        String taskLine = "[" + list[index].getStatusIcon() + "] " + list[index].getDescription();
                        System.out.println(taskLine);
                        System.out.println("____________________________________________________________\n");
                    }
                } else {
                    String[] inputArgs = input.split("\\s?/");
                    String[] mainCmd = inputArgs[0].split("\\s+", 2);
                    if (mainCmd.length < 2) {
                        throw new JaneException("The description of this " + mainCmd[0].toUpperCase() + " task should not be empty.");
                    }

                    HashMap<String, String> flags = new HashMap<String, String>();
                    for (int i = 1; i < inputArgs.length; ++i) {
                        String[] flagParts = inputArgs[i].split("\\s+", 2);
                        if (flagParts.length < 2) {
                            throw new JaneException("Invalid format of time given.");
                        }

                        flags.put(flagParts[0], flagParts[1]);
                    }
                    TaskType type;
                    try {
                        type = TaskType.valueOf(mainCmd[0].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new JaneException("Unknown task type: " + mainCmd[0]);
                    }

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

                            if (fromTime.isEmpty() || toTime.isEmpty()) {
                                throw new JaneException("Both /from and /to must be provided for an EVENT task.");
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
            } catch (JaneException e) {
                System.out.println("____________________________________________________________");
                System.out.println("Oh no! " + e.getMessage());
                System.out.println("____________________________________________________________\n");
            }
        }
        scanner.close();
    }
}