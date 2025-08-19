import java.util.Scanner;

public class Jane {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String greeting = "____________________________________________________________\n" +
                "Hello! I'm Jane\n" +
                "What can I do for you?\n" +
                "____________________________________________________________\n";
        System.out.println(greeting);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                String bye = "____________________________________________________________\n" +
                        "Bye. Hope to see you again soon!\n" +
                        "____________________________________________________________\n";
                System.out.println(bye);
                break;
            }

            String text = "____________________________________________________________\n" +
                    input +
                    "\n" +
                    "____________________________________________________________\n";
            System.out.println(text);
        }

        scanner.close();
    }
}