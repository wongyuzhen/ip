package jane;

/**
 * A GUI-specific {@link Ui} implementation that captures messages into
 * a buffer rather than printing them to the console.
 *
 * <p>This class is used in the GUI version of Jane to build responses
 * that can be rendered on-screen. Unlike the standard {@link Ui}, this
 * implementation emphasizes a playful, exaggerated "goofy" personality.</p>
 *
 * <h2>Style goals:</h2>
 * <ul>
 *     <li>Over-the-top phrasing and dramatic sound effects.</li>
 *     <li>Enthusiastic acknowledgements (e.g., "KABOOM!", "Ta-da!").</li>
 *     <li>Humorous takes on tasks, numbers, and daily life.</li>
 *     <li>No emojis, just text-based flair.</li>
 * </ul>
 */
public class GuiUi extends Ui {
    private final StringBuilder buf = new StringBuilder();

    /**
     * Returns the correct singular/plural form of a word.
     *
     * @param n        number to check
     * @param singular word to use if {@code n == 1}
     * @param plural   word to use otherwise
     * @return grammatically correct form
     */
    private static String plural(int n, String singular, String plural) {
        return n == 1 ? singular : plural;
    }

    /** Clears the internal output buffer. */
    public void clear() {
        buf.setLength(0);
    }

    /**
     * Returns the accumulated output as a string.
     *
     * @return buffered output text
     */
    public String output() {
        return buf.toString().trim();
    }

    /**
     * Appends multiple lines of text to the buffer, each followed by a newline.
     *
     * @param lines text lines to append
     */
    private void appendLines(String... lines) {
        for (String line : lines) {
            buf.append(line).append("\n");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void showWelcome() {
        appendLines(
                "*drumroll intensifies*",
                "HELLOOO! I'm Jane, your slightly unhinged task overlord.",
                "What chaos shall we unleash today?"
        );
    }

    /** {@inheritDoc} */
    @Override
    public void showError(String msg) {
        appendLines(
                "Whoopsie daisy! Jane tripped over a cable.",
                "Here's the mess I made: " + msg,
                "Try again before I embarrass myself further."
        );
    }

    /** {@inheritDoc} */
    @Override
    public void showBye() {
        appendLines(
                "*Jane does a dramatic stage exit*",
                "Farewell, brave adventurer. May your snacks be plentiful."
        );
    }

    /** {@inheritDoc} */
    @Override
    public void showAdded(Task t, int size) {
        buf.append("KABOOM! A wild task has appeared:\n")
                .append(t).append("\nYou are now juggling ").append(size).append(" ")
                .append(plural(size, "thingamajig", "thingamajigs"))
                .append(". Try not to drop them!\n");
    }

    /** {@inheritDoc} */
    @Override
    public void showRemoved(Task t, int size) {
        buf.append("ZAP! A task has been banished into the void:\n")
                .append(t).append("\nRemaining loot: ").append(size).append(" ")
                .append(plural(size, "quest", "quests"))
                .append(". Choose wisely.\n");
    }

    /** {@inheritDoc} */
    @Override
    public void showMarked(Task t, boolean done) {
        buf.append(done
                        ? "VICTORY! Task obliterated and marked complete:\n"
                        : "Plot twist! Task resurrected and marked incomplete:\n")
                .append(t).append("\n");
    }

    /** {@inheritDoc} */
    @Override
    public void showList(TaskList tasks) {
        if (tasks.size() == 0) {
            appendLines("Your task list is emptier than a popcorn bucket after movie night. Add something already!");
            return;
        }
        buf.append("Here comes your legendary scroll of chores:\n");
        for (int i = 0; i < tasks.size(); i++) {
            buf.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void showFoundTasks(TaskList tasks) {
        if (tasks.size() == 0) {
            appendLines("I searched high, low, and under the couch cushions... nothing found.");
            return;
        }
        buf.append("Ta-da! Here are the shiny matches I dug up:\n");
        for (int i = 0; i < tasks.size(); i++) {
            buf.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void showReminders(
            TaskList tasks,
            java.time.LocalDateTime from,
            java.time.LocalDateTime to,
            java.time.format.DateTimeFormatter fmt
    ) {
        appendLines(
                "Attention! Time wizard Jane has spoken.",
                "Between " + from.format(fmt) + " and " + to.format(fmt) + ", here’s what destiny holds:"
        );
        if (tasks.size() == 0) {
            appendLines("Absolutely nothing. Go touch some grass.");
            return;
        }
        for (int i = 0; i < tasks.size(); i++) {
            appendLines((i + 1) + ". " + tasks.get(i));
        }
    }
}
