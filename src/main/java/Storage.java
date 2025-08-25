import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path dataFile;

    public Storage(String filePath) {
        this.dataFile = Paths.get(filePath);
    }

    // Create directory and storage file if not already in existence
    private void ensureFileReady() throws IOException {
        Path parent = dataFile.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
        }
    }

    // Load tasks from storage into chatbot
    public ArrayList<Task> load() {
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
            // If corruption happens, start clean but don't crash the app
            System.out.println("[Storage] Warning: could not fully load data: " + e.getMessage());
        }
        return list;
    }

    // Save newly created task into storage
    public void save(List<Task> tasks) {
        try {
            ensureFileReady();
            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(toStorageString(task));
            }
            Files.write(dataFile, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.out.println("[Storage] Error saving tasks: " + e.getMessage());
        }
    }

    private String toStorageString(Task t) {
        String done = t.isDone ? "1" : "0";
        switch (t.type) {
            case TODO:
                return "TODO|" + done + "|" + t.description;
            case DEADLINE:
                return "DEADLINE|" + done + "|" + t.description + "|" + t.deadlineDate;
            case EVENT:
                return "EVENT|" + done + "|" + t.description + "|" + t.fromTime + "|" + t.toTime;
            default:
                throw new IllegalStateException("Unknown task type: " + t.type);
        }
    }

    private Task fromStorageString(String s) {
        String[] parts = s.split("\\|", -1);
        String type = parts[0];
        boolean done = "1".equals(parts[1]);
        Task t;

        switch (type) {
            case "TODO":
                t = new Task(parts[2]);
                break;
            case "DEADLINE":
                t = new Task(parts[2], parts[3]);
                break;
            case "EVENT":
                t = new Task(parts[2], parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }
        if (done) t.markAsDone();
        return t;
    }
}
