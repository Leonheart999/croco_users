package ge.lchitiashvili.croco_users.config.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.data.domain.Page;

import java.io.IOException;

public class CustomPageSerializer extends JsonSerializer<Page<?>> {
	@Override
	public void serialize(Page value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeStartObject();
		gen.writeNumberField("totalElements", value.getTotalElements());
		gen.writeNumberField("totalPages", value.getTotalPages());
		gen.writeNumberField("numberOfElements", value.getNumberOfElements());
		gen.writeNumberField("number", value.getNumber());
		gen.writeNumberField("size", value.getSize());
		gen.writeBooleanField("first", value.isFirst());
		gen.writeBooleanField("last", value.isLast());
		gen.writeFieldName("content");
		serializers.defaultSerializeValue(value.getContent(), gen);
		gen.writeEndObject();
	}
}
