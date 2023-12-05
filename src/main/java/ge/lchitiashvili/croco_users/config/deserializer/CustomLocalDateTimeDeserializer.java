package ge.lchitiashvili.croco_users.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ge.lchitiashvili.croco_users.config.util.Utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
	@Override
	public LocalDateTime deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
		String[] formats = {
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
				"yyyy-MM-dd",
				"dd.MM.yyyy",
				"dd/MM/yyyy",
				"dd.MM.yyyy HH:mm",
				"dd.MM.yyyy HH:mm:ss"
		};
		String date = jsonparser.getText();
		if (date == null || date.isEmpty()) {
			return null;
		}

		try {
			LocalDateTime d = Utils.getDateTimeFromTimestamp(Long.parseLong(date));
			if (d != null) {
				return d;
			}
		} catch (NumberFormatException ignored) {
		}

		for (String format : formats) {
			try {
				return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
			} catch (DateTimeParseException ignored) {
			}
		}

		throw new RuntimeException(String.format("Date format must be one of these: %s", String.join(", ", formats)));
	}
}