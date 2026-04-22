package restaurant.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static String format(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    public static String nowFormatted() {
        return format(LocalDateTime.now());
    }

    public static String convertZone(LocalDateTime dateTime, String zone) {

        ZonedDateTime zonedDateTime =
                dateTime.atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(ZoneId.of(zone));

        return zonedDateTime.format(FORMATTER);
    }
}