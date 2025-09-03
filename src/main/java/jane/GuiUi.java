package jane;

/**
 * A GUI-specific Ui that captures messages into a string buffer instead of printing to console.
 * Messages are the same as CLI Jane, but without separator lines.
 */
public class GuiUi extends Ui {
    private final StringBuilder buf = new StringBuilder();

    public void clear() { buf.setLength(0); }
    public String output() { return buf.toString().trim(); }

    private void appendLines(String... lines) {
        for (String line : lines) {
            buf.append(line).append("\n");
        }
    }

    @Override
    public void showWelcome() {
        appendLines("Hello! I'm Jane", "What can I do for you?");
    }

    @Override
    public void showError(String msg) {
        appendLines("Oh no! " + msg);
    }

    @Override
    public void showBye() {
        appendLines("Bye. Hope to see you again soon!");
    }
}
