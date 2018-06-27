package by.bsu.rfe.smsservice.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

/**
 * Util class, provides date converters.
 */
public final class JSR310DateConverters {

    /**
     * Private default c-r.
     */
    private JSR310DateConverters() {
    }

    /**
     * Locla date to date converter class.
     */
    public static final class LocalDateToDateConverter implements Converter<LocalDate, Date> {

        public static final LocalDateToDateConverter INSTANCE = new LocalDateToDateConverter();

        /**
         * Private default c-r.
         */
        private LocalDateToDateConverter() {
        }

        @Override
        public Date convert(LocalDate source) {
            if (source == null) {
                return null;
            } else {
                return Date.from(source.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        }
    }

    /**
     * Date to local date converter class.
     */
    public static final class DateToLocalDateConverter implements Converter<Date, LocalDate> {
        public static final DateToLocalDateConverter INSTANCE = new DateToLocalDateConverter();

        /**
         * Private default c-r.
         */
        private DateToLocalDateConverter() {
        }

        @Override
        public LocalDate convert(Date source) {
            if (source == null) {
                return null;
            } else {
                return ZonedDateTime.ofInstant(source.toInstant(),
                    ZoneId.systemDefault()).toLocalDate();
            }
        }
    }

    /**
     * Zoned date to date converter class.
     */
    public static final class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {
        public static final ZonedDateTimeToDateConverter INSTANCE = new ZonedDateTimeToDateConverter();

        /**
         * Private default c-r.
         */
        private ZonedDateTimeToDateConverter() {
        }

        @Override
        public Date convert(ZonedDateTime source) {
            if (source == null) {
                return null;
            } else {
                return Date.from(source.toInstant());
            }
        }
    }

    /**
     * Date to zoned date time converter class.
     */
    public static final class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
        public static final DateToZonedDateTimeConverter INSTANCE = new DateToZonedDateTimeConverter();

        /**
         * Private default c-r.
         */
        private DateToZonedDateTimeConverter() {
        }

        @Override
        public ZonedDateTime convert(Date source) {
            if (source == null) {
                return null;
            } else {
                return ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
            }
        }
    }

    /**
     * Local date time to date converter class.
     */
    public static final class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {
        public static final LocalDateTimeToDateConverter INSTANCE = new LocalDateTimeToDateConverter();

        /**
         * Private default c-r.
         */
        private LocalDateTimeToDateConverter() {
        }

        @Override
        public Date convert(LocalDateTime source) {
            if (source == null) {
                return null;
            } else {
                return Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
            }
        }
    }

    /**
     * Date to local date time converter.
     */
    public static final class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {
        public static final DateToLocalDateTimeConverter INSTANCE = new DateToLocalDateTimeConverter();

        /**
         * Private default c-r.
         */
        private DateToLocalDateTimeConverter() {
        }

        @Override
        public LocalDateTime convert(Date source) {
            if (source == null) {
                return null;
            } else {
                return LocalDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());
            }
        }
    }
}
