package jane;

import java.time.*;
import java.time.format.*;
import java.util.Locale;

/**
 * Utility class for handling date and time parsing, formatting, and storage.
 * Provides methods to parse dates and times in multiple formats, format them for display,
 * and handle storage-friendly formats.
 */
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

    /**
     * Parses a date from the given string in supported formats (yyyy-MM-dd, d/M/yyyy).
     *
     * @param raw The raw date string to parse.
     * @return The parsed LocalDate.
     * @throws DateTimeParseException If the date format is unrecognized.
     */
    public static LocalDate parseDate(String raw) {
        String s = raw.trim();
        for (DateTimeFormatter f : new DateTimeFormatter[]{DATE_IN_1, DATE_IN_2}) {
            try { return LocalDate.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        throw new DateTimeParseException("Unrecognized date", s, 0);
    }

    /**
     * Parses a time from the given string in supported formats (HHmm, HH:mm).
     *
     * @param raw The raw time string to parse.
     * @return The parsed LocalTime.
     * @throws DateTimeParseException If the time format is unrecognized.
     */
    public static LocalTime parseTime(String raw) {
        String s = raw.trim();
        for (DateTimeFormatter f : new DateTimeFormatter[]{TIME_IN_1, TIME_IN_2}) {
            try { return LocalTime.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        throw new DateTimeParseException("Unrecognized time", s, 0);
    }

    /**
     * Parses a datetime from the given string in supported formats.
     *
     * @param raw The raw datetime string to parse.
     * @return The parsed LocalDateTime.
     * @throws DateTimeParseException If the datetime format is unrecognized.
     */
    public static LocalDateTime parseDateTime(String raw) {
        String s = raw.trim();
        for (DateTimeFormatter f : new DateTimeFormatter[]{DATETIME_IN_1, DATETIME_IN_2, DATETIME_IN_3, DATETIME_IN_4}) {
            try { return LocalDateTime.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        throw new DateTimeParseException("Unrecognized datetime", s, 0);
    }

    /**
     * Formats a LocalDate to a user-friendly format (MMM dd yyyy).
     *
     * @param d The LocalDate to format.
     * @return The formatted date string (e.g., "Oct 15 2019").
     */
    public static String formatDate(LocalDate d) {
        return DATE_OUT.format(d);
    }

    /**
     * Formats a LocalDateTime to a user-friendly format (MMM dd yyyy, h:mm a).
     *
     * @param dt The LocalDateTime to format.
     * @return The formatted datetime string (e.g., "Oct 15 2019, 6:00 PM").
     */
    public static String formatDateTime(LocalDateTime dt) {
        return DATETIME_OUT.format(dt);
    }

    /**
     * Converts a LocalDate to a storage-friendly format (yyyy-MM-dd).
     *
     * @param d The LocalDate to store.
     * @return The formatted date string for storage.
     */
    public static String storeDate(LocalDate d) { return DATE_STORE.format(d); }

    /**
     * Converts a LocalDateTime to a storage-friendly format (yyyy-MM-dd HHmm).
     *
     * @param dt The LocalDateTime to store.
     * @return The formatted datetime string for storage.
     */
    public static String storeDateTime(LocalDateTime dt) { return DATETIME_STORE.format(dt); }

    /**
     * Loads a LocalDate from a storage-friendly formatted string (yyyy-MM-dd).
     *
     * @param s The storage string to parse.
     * @return The parsed LocalDate.
     */
    public static LocalDate loadDate(String s) { return LocalDate.parse(s.trim(), DATE_STORE); }

    /**
     * Loads a LocalDateTime from a storage-friendly formatted string (yyyy-MM-dd HHmm).
     *
     * @param s The storage string to parse.
     * @return The parsed LocalDateTime.
     */
    public static LocalDateTime loadDateTime(String s) { return LocalDateTime.parse(s.trim(), DATETIME_STORE); }
}
