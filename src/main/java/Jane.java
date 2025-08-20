import java.util.ArrayList;
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

        ArrayList<Task> list = new ArrayList<>();

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
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println((i + 1) + ". " + list.get(i));
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
                    if (index < 0 || index >= list.size()) {
                        throw new JaneException("Task number " + (index + 1) + " does not exist.");
                    }

                    Task task = list.get(index);
                    if (words[0].equals("mark")) {
                        task.markAsDone();
                        System.out.println("____________________________________________________________\n" +
                                "Nice! I've marked this task as done:\n" +
                                task + "\n" +
                                "____________________________________________________________\n");
                    } else {
                        task.markAsUndone();
                        System.out.println("____________________________________________________________\n" +
                                "OK, I've marked this task as not done yet:\n" +
                                task + "\n" +
                                "____________________________________________________________\n");
                    }
                } else if (words[0].equals("delete")) {
                    if (words.length < 2) {
                        throw new JaneException("Please specify a task number.");
                    }

                    int index;
                    try {
                        index = Integer.parseInt(words[1]) - 1;
                    } catch (NumberFormatException e) {
                        throw new JaneException("Invalid task number: " + words[1]);
                    }

                    if (index < 0 || index >= list.size()) {
                        throw new JaneException("Task number " + (index + 1) + " does not exist.");
                    }

                    Task removed = list.remove(index);
                    System.out.println("____________________________________________________________\n" +
                            "Noted. I've removed this task:\n" +
                            removed + "\n" +
                            "Now you have " + list.size() + " tasks in the list.\n" +
                            "____________________________________________________________\n");

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

                    Task task;
                    
                    switch (type) {
                        case TODO:
                            task = new Task(mainCmd[1]);
                            break;
                        case DEADLINE:
                            String by = flags.getOrDefault("by", "");
                            if (by.isEmpty()) {
                                throw new JaneException("Deadline task must have a /by date.");
                            }
                            task = new Task(mainCmd[1], by);
                            break;
                        case EVENT:
                            String from = flags.get("from");
                            String to = flags.get("to");
                            if (from == null || to == null) {
                                throw new JaneException("Event task must have both /from and /to times.");
                            }
                            task = new Task(mainCmd[1], from, to);
                            break;
                        default:
                            throw new JaneException("Unhandled task type: " + mainCmd[0]);
                    }

                    list.add(task);

                    System.out.println("____________________________________________________________\n" +
                            "Got it. I've added this task:\n" +
                            task + "\n" +
                            "Now you have " + list.size() + " tasks in the list.\n" +
                            "____________________________________________________________\n");
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