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
        String list[] = new String[100];

        while (true) {
            String input = scanner.nextLine();

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
                        System.out.println((i + 1) + ". " + list[i] + "\n");
                    }
                }
                System.out.println("____________________________________________________________\n");
            } else {
                list[count] = input;
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