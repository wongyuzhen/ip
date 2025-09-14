package jane;

/**
 * A GUI-specific Ui that captures messages into a string buffer instead of printing to console.
 * This version gives Jane a goofy, over-the-top personality without using emojis.
 *
 * Style goals:
 * - Exaggerated phrasing, playful exaggerations.
 * - Over-enthusiastic acknowledgements ("KABOOM!", "Ta-da!", "Whoopsie daisy!").
 * - Jokes about tasks, numbers, and everyday life.
 *
 * [AI assist: GPT-5 Thinking]
 * - Designed a consistent “goofy voice” lexicon with no emoji dependency.
 * - Suggested dramatic sound effects to replace visual cues.
 * - Maintained pluralization helper for clean grammar.
 */
public class GuiUi extends Ui {
    private final StringBuilder buf = new StringBuilder();

    // Helper for plural grammar
    private static String plural(int n, String singular, String plural) {
        return n == 1 ? singular : plural;
    }

    public void clear() {
        buf.setLength(0);
    }

    public String output() {
        return buf.toString().trim();
    }

    private void appendLines(String... lines) {
        for (String line : lines) {
            buf.append(line).append("\n");
        }
    }

    @Override
    public void showWelcome() {
        appendLines(
                "*drumroll intensifies*",
                "HELLOOO! I'm Jane, your slightly unhinged task overlord.",
                "What chaos shall we unleash today?"
        );
    }

    @Override
    public void showError(String msg) {
        appendLines(
                "Whoopsie daisy! Jane tripped over a cable.",
                "Here's the mess I made: " + msg,
                "Try again before I embarrass myself further."
        );
    }

    @Override
    public void showBye() {
        appendLines(
                "*Jane does a dramatic stage exit*",
                "Farewell, brave adventurer. May your snacks be plentiful."
        );
    }

    @Override
    public void showAdded(Task t, int size) {
        buf.append("KABOOM! A wild task has appeared:\n")
                .append(t).append("\nYou are now juggling ").append(size).append(" ")
                .append(plural(size, "thingamajig", "thingamajigs"))
                .append(". Try not to drop them!\n");
    }

    @Override
    public void showRemoved(Task t, int size) {
        buf.append("ZAP! A task has been banished into the void:\n")
                .append(t).append("\nRemaining loot: ").append(size).append(" ")
                .append(plural(size, "quest", "quests"))
                .append(". Choose wisely.\n");
    }

    @Override
    public void showMarked(Task t, boolean done) {
        buf.append(done
                        ? "VICTORY! Task obliterated and marked complete:\n"
                        : "Plot twist! Task resurrected and marked incomplete:\n")
                .append(t).append("\n");
    }

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
