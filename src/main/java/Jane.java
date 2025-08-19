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
        Task list[] = new Task[100];

        while (true) {
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
                System.out.println("____________________________________________________________\n");
                for (int i = 0; i < 100; i++) {
                    if (list[i] != null) {
                        System.out.println((i + 1) + ". [" + list[i].getStatusIcon() + "] " +
                                list[i].getDescription() + "\n");
                    }
                }
                System.out.println("____________________________________________________________\n");
            } else if (words[0].equals("mark")) {
                int index = Integer.parseInt(words[1]) - 1;
                list[index].markAsDone();
                System.out.println("____________________________________________________________\n" +
                    "Nice! I've marked this task as done:\n");
                String taskLine = "[" + list[index].getStatusIcon() + "] " + list[index].getDescription();
                System.out.println(taskLine + "\n");
                System.out.println("____________________________________________________________\n");
            } else if (words[0].equals("unmark")) {
                int index = Integer.parseInt(words[1]) - 1;
                list[index].markAsUndone();
                System.out.println("____________________________________________________________\n" +
                        "OK, I've marked this task as not done yet:\n");
                String taskLine = "[" + list[index].getStatusIcon() + "] " + list[index].getDescription();
                System.out.println(taskLine + "\n");
                System.out.println("____________________________________________________________\n");
            } else {
                list[count] = new Task(input);
                count++;

                String text = "____________________________________________________________\n" +
                        "added: " +
                        input +
                        "\n" +
                        "____________________________________________________________\n";
                System.out.println(text);
            }
        }

        scanner.close();
    }
}