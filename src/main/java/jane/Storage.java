package jane;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles reading and writing tasks to a file.
 * Provides methods for loading tasks from storage and saving tasks back to storage file.
 * <p>Tasks are serialized and deserialized from a text-based format to ensure persistence between sessions.</p>
 *
 * <p>This class also ensures that the storage file exists and can create missing directories and files as needed.</p>
 */
public class Storage {
    private final Path dataFile;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path to the storage file.
     */
    public Storage(String filePath) {
        this.dataFile = Paths.get(filePath);
    }

    /**
     * Ensures the existence of the storage file and its parent directories.
     * Creates missing directories and the file itself if they do not already exist.
     *
     * @throws IOException If an I/O error occurs during file or directory creation.
     */
    private void ensureFileReady() throws IOException {
        Path parent = dataFile.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }
    }

    /**
     * Loads tasks from the storage file.
     * Reads all lines from the file and deserializes them into Task objects.
     *
     * @return An ArrayList of tasks loaded from storage.
     * @throws JaneException If an error occurs while loading the tasks (e.g., file format issues).
     */
    public ArrayList<Task> load() throws JaneException {
        ArrayList<Task> list = new ArrayList<>();
        try {
            if (!Files.exists(dataFile)) {
                ensureFileReady();
                return list;
            }
            List<String> lines = Files.readAllLines(dataFile, StandardCharsets.UTF_8);
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                list.add(fromStorageString(line));
            }
        } catch (Exception e) {
            throw new JaneException("Could not load tasks from storage.", e);
        }
        return list;
    }

    /**
     * Saves a list of tasks to the storage file.
     * Each task is serialized into a string and written to the file, replacing any existing content.
     *
     * @param tasks The list of tasks to be saved.
     * @throws JaneException If an error occurs while saving tasks to the file.
     */
    public void save(List<Task> tasks) throws JaneException {
        try {
            ensureFileReady();
            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(toStorageString(task));
            }
            Files.write(dataFile, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new JaneException("Could not save tasks to storage.", e);
        }
    }

    /**
     * Serializes a task to a string that can be stored in the file.
     *
     * @param t The task to be serialized.
     * @return The string representation of the task for storage.
     */
    private String toStorageString(Task t) {
        String done = t.isDone ? "1" : "0";
        switch (t.type) {
            case TODO:
                return "TODO|" + done + "|" + t.description;
            case DEADLINE: {
                String d = DateTimeUtil.storeDate(t.deadlineDate); // yyyy-MM-dd
                return "DEADLINE|" + done + "|" + t.description + "|" + d;
            }
            case EVENT: {
                String from = DateTimeUtil.storeDateTime(t.fromTime); // yyyy-MM-dd HHmm
                String to   = DateTimeUtil.storeDateTime(t.toTime);   // yyyy-MM-dd HHmm
                return "EVENT|" + done + "|" + t.description + "|" + from + "|" + to;
            }
            default:
                throw new IllegalStateException("Unknown task type: " + t.type);
        }
    }

    /**
     * Deserializes a string into a Task object.
     * This method assumes the input string is in the correct format for a task (TODO, DEADLINE, EVENT).
     *
     * @param s The string to be deserialized.
     * @return The Task object created from the string.
     * @throws IllegalArgumentException If the string is in an invalid format.
     */
    private Task fromStorageString(String s) {
        String[] p = s.split("\\|", -1);
        String type = p[0];
        boolean done = "1".equals(p[1]);
        Task t;
        switch (type) {
            case "TODO":
                t = new Task(p[2]);
                break;
            case "DEADLINE": {
                LocalDate d = DateTimeUtil.loadDate(p[3]);
                t = new Task(p[2], d);
                break;
            }
            case "EVENT": {
                LocalDateTime from = DateTimeUtil.loadDateTime(p[3]);
                LocalDateTime to   = DateTimeUtil.loadDateTime(p[4]);
                t = new Task(p[2], from, to);
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
        if (done) t.markAsDone();
        return t;
    }
}
