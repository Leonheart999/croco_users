package ge.lchitiashvili.croco_users.config.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {
	@Override
	public Date deserialize(JsonParser jsonparser,
							DeserializationContext deserializationcontext) throws IOException {
		SimpleDateFormat sdf;
		String date;
		String[] formats = {
				"yyyy-MM-dd'T'HH:mm:ss'Z'",
				"yyyy-MM-dd'T'HH:mm:ssZ",
				"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
				"yyyy-MM-dd'T'HH:mm:ss.SSSZ",
				"EEE, d MMM yyyy HH:mm:ss",
				"EEE, dd MMM yyyy HH:mm:ss zzz",
				"dd.MM.yyyy HH:mm:ss",
				"dd.MM.yyyy HH:mm",
				"yyyy-MM-dd",
				"dd.MM.yyyy",
				"dd/MM/yyyy",
		};
		date = jsonparser.getText();
		if (date == null || date.isEmpty()) {
			return null;
		}
		try {
			return new Date(Long.parseLong(jsonparser.getText()) * 1000);
		} catch (NumberFormatException ignored) {
		}

		for (String format : formats) {
			sdf = new SimpleDateFormat(format);
			try {
				return sdf.parse(date);
			} catch (ParseException ignored) {
			}
		}

		throw new RuntimeException(String.format("Date format must be one of these: %s", String.join(", ", formats)));
	}
}
