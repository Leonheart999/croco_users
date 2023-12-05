package ge.lchitiashvili.croco_users.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ge.lchitiashvili.croco_users.config.util.Utils;
import io.micrometer.common.util.StringUtils;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {
	private static final String[] formats = {
			"yyyy-MM-dd",
			"dd.MM.yyyy",
			"MM/dd/yyyy",
			"dd/MM/yyyy",
			"yyyy-MM-dd'T'HH:mm:ss'Z'",
			"yyyy-MM-dd'T'HH:mm:ss",
			"yyyy-MM-dd' 'HH:mm:ss'Z'",
			"yyyy-MM-dd'T'HH:mm:ssZ",
			"yyyy-MM-dd' 'HH:mm:ssZ",
			"yyyy-MM-dd' 'HH:mm:ss",
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			"EEE, d MMM yyyy HH:mm:ss",
			"EEE, dd MMM yyyy HH:mm:ss zzz",
			"dd.MM.yyyy HH:mm",
			"dd.MM.yyyy HH:mm:ss"
	};


	@Override
	public LocalDate deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
		String date = jsonparser.getText();
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		try {
			LocalDateTime d = Utils.getDateTimeFromTimestamp(Long.parseLong(date));
			if (d != null) {
				return d.toLocalDate();
			}
		} catch (NumberFormatException ignored) {
		}
		for (String format : formats) {
			try {
				return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
			} catch (DateTimeParseException ignore) {
			}
		}
		throw new RuntimeException(String.format("Date format must be one of these: %s", String.join(", ", formats)));
	}
}
