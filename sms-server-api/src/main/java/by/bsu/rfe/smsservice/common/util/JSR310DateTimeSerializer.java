package by.bsu.rfe.smsservice.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * JSR310 Date Time Serializer.
 */
public final class JSR310DateTimeSerializer extends JsonSerializer<TemporalAccessor> {

    private static final DateTimeFormatter ISO_FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    public static final JSR310DateTimeSerializer INSTANCE = new JSR310DateTimeSerializer();

    /**
     * Default c-r.
     */
    private JSR310DateTimeSerializer() {
    }

    @Override
    public void serialize(TemporalAccessor value, JsonGenerator generator, SerializerProvider serializerProvider)
        throws IOException {
        generator.writeString(ISO_FORMATTER.format(value));
    }
}
