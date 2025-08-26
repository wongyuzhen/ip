package jane;

import jane.command.*;

import java.util.HashMap;

/**
 * Parses the user's input and returns the appropriate Command.
 * This takes care of recognizing different task-related commands and creating the corresponding command objects.
 *
 */
public class Parser {

    /**
     * Parses the input command and returns the right Command based on the user’s input.
     * Recognizes commands like "bye", "list", "mark", "unmark", "delete", and task creation commands.
     *
     * @param input The command input.
     * @return The Command object for the user’s action.
     * @throws JaneException If the input is invalid or the command can't be processed.
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
        }
        catch (NumberFormatException e) {
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

        HashMap<String,String> flags = new HashMap<>();
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
}