package jane;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilTest {

    @Test
    void parseDate_acceptsMultiplePatterns() {
        assertEquals(LocalDate.of(2019, 10, 15),
                DateTimeUtil.parseDate("2019-10-15"));
        assertEquals(LocalDate.of(2019, 12, 2),
                DateTimeUtil.parseDate("2/12/2019"));
    }

    @Test
    void parseDateTime_acceptsMultiplePatterns() {
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0),
                DateTimeUtil.parseDateTime("2/12/2019 1800"));
        assertEquals(LocalDateTime.of(2019, 10, 15, 18, 0),
                DateTimeUtil.parseDateTime("2019-10-15 18:00"));
    }

    @Test
    void parseTime_acceptsMultiplePatterns_colon_mm() {
        assertEquals(LocalTime.of(18, 0), DateTimeUtil.parseTime("1800"));
        assertEquals(LocalTime.of(18, 0), DateTimeUtil.parseTime("18:00"));
    }
}
