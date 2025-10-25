package br.com.camargo.hotel.management.commons.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
    public static final String DEFAULT_DATA_HORA_FORMAT = "dd/MM/yyyy HH:mm";
    private static final String DEFAULT_DATA_FORMAT = "dd/MM/yyyy";

    public static String format(LocalDate localDate) {
        if (localDate == null) return "";
        return DateTimeFormatter.ofPattern(DEFAULT_DATA_FORMAT).format(localDate);
    }

    public static String format(LocalDateTime localDateTime) {
        if (localDateTime == null) return "";
        return DateTimeFormatter.ofPattern(DEFAULT_DATA_HORA_FORMAT).format(localDateTime);
    }

    public static boolean isFinalDeSemana(LocalDateTime data) {
        return isFinalDeSemana(data.toLocalDate());
    }

    public static boolean isFinalDeSemana(LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();
        return dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
    }
}
