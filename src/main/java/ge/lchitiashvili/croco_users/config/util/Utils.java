package ge.lchitiashvili.croco_users.config.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class Utils {

    public static LocalDateTime getDateTimeFromTimestamp(long timestamp) {
        if (timestamp == 0)
            return null;

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId());
    }
}
