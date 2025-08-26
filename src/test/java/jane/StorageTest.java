package jane;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StorageTest {

    @TempDir
    Path tmp;

    @Test
    void saveAndLoad_roundTrip_preservesDataAndFormat() throws Exception {
        Path file = tmp.resolve("tasks.txt");
        Storage storage = new Storage(file.toString());

        // Prepare tasks in memory
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Task("read book")); // TODO
        tasks.add(new Task("submit report", LocalDate.of(2019, 12, 2))); // DEADLINE (LocalDate)
        tasks.add(new Task("party",
                LocalDateTime.of(2019, 12, 2, 20, 0),
                LocalDateTime.of(2019, 12, 2, 22, 0))); // EVENT (LocalDateTime)

        // Write to disk
        storage.save(tasks);

        // Check raw on-disk lines
        List<String> lines = Files.readAllLines(file);
        assertEquals(3, lines.size(), "Should write one line per task");

        assertTrue(lines.stream().anyMatch(s -> s.equals("TODO|0|read book")));

        // deadline must be date-only yyyy-MM-dd
        assertTrue(lines.stream().anyMatch(s -> s.equals("DEADLINE|0|submit report|2019-12-02")),
                "Deadline should store as yyyy-MM-dd only");

        // event must be yyyy-MM-dd HHmm for both from and to
        assertTrue(lines.stream().anyMatch(s -> s.equals("EVENT|0|party|2019-12-02 2000|2019-12-02 2200")),
                "Event should store yyyy-MM-dd HHmm for both from and to");

        // Load from disk
        ArrayList<Task> loaded = new Storage(file.toString()).load();

        // Check same number of tasks
        assertEquals(tasks.size(), loaded.size());

        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i).toString(), loaded.get(i).toString());
        }
    }

    @Test
    void load_throwsJaneException_onCorruptLine() throws Exception {
        Path file = tmp.resolve("bad.txt");
        Files.write(file, List.of("GARBAGE|line|that|won't|parse"));
        Storage storage = new Storage(file.toString());
        assertThrows(JaneException.class, storage::load);
    }
}
