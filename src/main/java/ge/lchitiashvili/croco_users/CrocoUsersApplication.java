package ge.lchitiashvili.croco_users;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import ge.lchitiashvili.croco_users.config.deserializer.CustomDateDeserializer;
import ge.lchitiashvili.croco_users.config.deserializer.CustomLocalDateDeserializer;
import ge.lchitiashvili.croco_users.config.deserializer.CustomLocalDateTimeDeserializer;
import ge.lchitiashvili.croco_users.config.serializer.CustomDateSerializer;
import ge.lchitiashvili.croco_users.config.serializer.CustomPageSerializer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

@EnableWebMvc
@EnableCaching
@EntityScan("ge.lchitiashvili.croco_users.models")
@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class})
@EnableJpaRepositories("ge.lchitiashvili.croco_users.repositories.jpa")
public class CrocoUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrocoUsersApplication.class, args);
	}
	@Bean
	public ObjectMapper objectMapper() {
		SimpleModule simpleModule = new SimpleModule("croco_users-module", Version.unknownVersion());
		simpleModule.addDeserializer(Date.class, new CustomDateDeserializer());
		simpleModule.addSerializer(Date.class, new CustomDateSerializer());

		simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
		simpleModule.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer());

		simpleModule.addDeserializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
		simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

		return new Jackson2ObjectMapperBuilder()
				.failOnUnknownProperties(false)
				.failOnEmptyBeans(false)
				.defaultViewInclusion(false)
				.timeZone(TimeZone.getDefault())
				.serializerByType(Page.class, new CustomPageSerializer())
				.build()
				.findAndRegisterModules()
				.registerModule(simpleModule);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mm= new ModelMapper();
		mm.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return mm;
	}
}
