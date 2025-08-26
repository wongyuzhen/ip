package jane;

import java.time.*;
import java.time.format.*;
import java.util.Locale;

public final class DateTimeUtil {
    private DateTimeUtil() {}

    // INPUT formats
    private static final DateTimeFormatter DATE_IN_1 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);       // 2019-10-15
    private static final DateTimeFormatter DATE_IN_2 =
            DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH);         // 2/12/2019
    private static final DateTimeFormatter TIME_IN_1 =
            DateTimeFormatter.ofPattern("HHmm", Locale.ENGLISH);             // 1800
    private static final DateTimeFormatter TIME_IN_2 =
            DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);            // 18:00
    private static final DateTimeFormatter DATETIME_IN_1 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm", Locale.ENGLISH);  // 2019-10-15 1800
    private static final DateTimeFormatter DATETIME_IN_2 =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH); // 2019-10-15 18:00
    private static final DateTimeFormatter DATETIME_IN_3 =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm", Locale.ENGLISH);    // 2/12/2019 1800
    private static final DateTimeFormatter DATETIME_IN_4 =
            DateTimeFormatter.ofPattern("d/M/yyyy HH:mm", Locale.ENGLISH);   // 2/12/2019 18:00

    // OUTPUT formats
    private static final DateTimeFormatter DATE_OUT =
            DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);      // Oct 15 2019
    private static final DateTimeFormatter DATETIME_OUT =
            DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a", Locale.ENGLISH); // Oct 15 2019, 6:00 PM

    // STORAGE formats
    private static final DateTimeFormatter DATE_STORE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    private static final DateTimeFormatter DATETIME_STORE =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm", Locale.ENGLISH);

    public static LocalDate parseDate(String raw) {
        String s = raw.trim();
        for (DateTimeFormatter f : new DateTimeFormatter[]{DATE_IN_1, DATE_IN_2}) {
            try { return LocalDate.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        throw new DateTimeParseException("Unrecognized date", s, 0);
    }

    public static LocalTime parseTime(String raw) {
        String s = raw.trim();
        for (DateTimeFormatter f : new DateTimeFormatter[]{TIME_IN_1, TIME_IN_2}) {
            try { return LocalTime.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        throw new DateTimeParseException("Unrecognized time", s, 0);
    }

    public static LocalDateTime parseDateTime(String raw) {
        String s = raw.trim();
        for (DateTimeFormatter f : new DateTimeFormatter[]{DATETIME_IN_1, DATETIME_IN_2, DATETIME_IN_3, DATETIME_IN_4}) {
            try { return LocalDateTime.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        throw new DateTimeParseException("Unrecognized datetime", s, 0);
    }

    // Display helpers
    public static String formatDate(LocalDate d) {
        return DATE_OUT.format(d);
    }
    public static String formatDateTime(LocalDateTime dt) {
        return DATETIME_OUT.format(dt);
    }

    // Storage helpers
    public static String storeDate(LocalDate d) { return DATE_STORE.format(d); }
    public static String storeDateTime(LocalDateTime dt) { return DATETIME_STORE.format(dt); }

    public static LocalDate loadDate(String s) { return LocalDate.parse(s.trim(), DATE_STORE); }
    public static LocalDateTime loadDateTime(String s) { return LocalDateTime.parse(s.trim(), DATETIME_STORE); }
}