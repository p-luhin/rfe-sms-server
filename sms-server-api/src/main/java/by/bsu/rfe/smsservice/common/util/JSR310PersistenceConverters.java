package by.bsu.rfe.smsservice.common.util;

import by.bsu.rfe.smsservice.common.util.JSR310DateConverters.DateToLocalDateTimeConverter;
import by.bsu.rfe.smsservice.common.util.JSR310DateConverters.DateToZonedDateTimeConverter;
import by.bsu.rfe.smsservice.common.util.JSR310DateConverters.LocalDateTimeToDateConverter;
import by.bsu.rfe.smsservice.common.util.JSR310DateConverters.ZonedDateTimeToDateConverter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Persistent converters.
 */
public final class JSR310PersistenceConverters {

    /**
     * Default c-r.
     */
    private JSR310PersistenceConverters() {
    }

    /**
     * Local Date Converter.
     */
    @Converter(autoApply = true)
    public static class LocalDateConverter implements AttributeConverter<LocalDate, java.sql.Date> {

        @Override
        public java.sql.Date convertToDatabaseColumn(LocalDate date) {
            if (date == null) {
                return null;
            } else {
                return java.sql.Date.valueOf(date);
            }
        }

        @Override
        public LocalDate convertToEntityAttribute(java.sql.Date date) {
            if (date == null) {
                return null;
            } else {
                return date.toLocalDate();
            }
        }
    }

    /**
     * Zoned Date Time Converter.
     */
    @Converter(autoApply = true)
    public static class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Date> {

        @Override
        public Date convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
            return ZonedDateTimeToDateConverter.INSTANCE.convert(zonedDateTime);
        }

        @Override
        public ZonedDateTime convertToEntityAttribute(Date date) {
            return DateToZonedDateTimeConverter.INSTANCE.convert(date);
        }
    }

    /**
     * Local Date Time Converter.
     */
    @Converter(autoApply = true)
    public static class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

        @Override
        public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
            return LocalDateTimeToDateConverter.INSTANCE.convert(localDateTime);
        }

        @Override
        public LocalDateTime convertToEntityAttribute(Date date) {
            return DateToLocalDateTimeConverter.INSTANCE.convert(date);
        }
    }
}
