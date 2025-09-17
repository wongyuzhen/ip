package jane;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jane.command.*;

/**
 * Responsible for parsing raw user input into executable {@link Command} objects.
 *
 * <p>The {@code Parser} interprets different types of task-related commands,
 * such as adding, deleting, marking, finding, and reminders, and delegates
 * execution to the appropriate {@code Command} subclass.</p>
 */
public class Parser {

    /**
     * Parses a line of user input and produces the corresponding {@link Command}.
     *
     * @param input raw user input string
     * @return a {@link Command} representing the action to execute
     * @throws JaneException if the input is invalid or the command is not recognized
     */
    public static Command parse(String input) throws JaneException {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            throw new JaneException("Empty command.");
        }

        String[] words = trimmed.split("\\s+");
        String head = words[0];

        switch (head) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "mark":
            return new MarkCommand(parseIndex(words, 1), true);
        case "unmark":
            return new MarkCommand(parseIndex(words, 1), false);
        case "delete":
            return new DeleteCommand(parseIndex(words, 1));
        case "find":
            return parseFind(trimmed, words);
        case "remindme":
            return parseRemind(trimmed);
        default:
            return parseAddLike(trimmed);
        }
    }

    /**
     * Extracts and returns the task index from the input.
     * If the index is invalid or missing, throws an error.
     *
     * @param words The split input command.
     * @param pos The position of the index in the input.
     * @return The index as an integer.
     * @throws JaneException If the index is invalid.
     */
    private static int parseIndex(String[] words, int pos) throws JaneException {
        if (words.length <= pos) {
            throw new JaneException("Please specify a task number.");
        }

        try {
            return Integer.parseInt(words[pos]) - 1;
        } catch (NumberFormatException e) {
            throw new JaneException("Invalid task number: " + words[pos]);
        }
    }

    /**
     * Handles task creation commands like "todo", "deadline", and "event".
     * Extracts the description and any flags like date/time for deadlines or events.
     *
     * @param full The full input string (e.g., "todo read book /by 2022-12-31").
     * @return The corresponding Command for the task.
     * @throws JaneException If the input format is invalid or missing required flags.
     */
    private static Command parseAddLike(String full) throws JaneException {
        String[] parts = full.split("\\s?/");
        String[] head = parts[0].trim().split("\\s+", 2);

        if (head.length < 2) {
            throw new JaneException(
                    "The description of this " + head[0].toUpperCase() + " task should not be empty."
            );
        }

        HashMap<String, String> flags = new HashMap<>();
        for (int i = 1; i < parts.length; i++) {
            String[] fp = parts[i].trim().split("\\s+", 2);
            if (fp.length < 2) {
                throw new JaneException("Invalid format of time given.");
            }

            flags.put(fp[0], fp[1]);
        }

        String type = head[0].toLowerCase();
        String desc = head[1];

        switch (type) {
        case "todo":
            return new TodoCommand(desc);
        case "deadline": {
            String by = flags.getOrDefault("by", "");
            if (by.isEmpty()) {
                throw new JaneException("Deadline task must have a /by date.");
            }
            return new DeadlineCommand(desc, by);
        }
        case "event": {
            String from = flags.get("from");
            String to = flags.get("to");
            if (from == null || to == null) {
                throw new JaneException("Event task must have both /from and /to times.");
            }
            return new EventCommand(desc, from, to);
        }
        default:
            throw new JaneException("Unknown task type: " + head[0]);
        }
    }

    /**
     * Parses a {@code find} command that searches tasks by keyword.
     *
     * @param full  full user input
     * @param words split user input
     * @return a {@link FindCommand} with the search term
     * @throws JaneException if no keyword is provided
     */
    private static Command parseFind(String full, String[] words) throws JaneException {
        if (words.length < 2) {
            throw new JaneException("Please specify a search keyword.");
        }
        return new FindCommand(full.substring(5)); // "find " = 5 chars
    }

    /**
     * Parses a {@code remindme} command with an optional number of weeks.
     *
     * @param full full user input
     * @return a {@link RemindCommand} with the requested duration
     * @throws JaneException if the format is invalid
     */
    private static Command parseRemind(String full) throws JaneException {
        int weeks = 1; // default
        String tail = full.substring("remindme".length()).trim();

        if (!tail.isEmpty()) {
            Matcher m = Pattern.compile("^/\\s*(\\d+)\\s*weeks?$", Pattern.CASE_INSENSITIVE).matcher(tail);
            if (m.find()) {
                weeks = Integer.parseInt(m.group(1));
            } else {
                throw new JaneException("Usage: remindme [/N weeks]");
            }
        }
        return new RemindCommand(Math.max(1, weeks));
    }
}
