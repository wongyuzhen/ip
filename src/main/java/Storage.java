import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
