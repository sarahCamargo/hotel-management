package br.com.camargo.hotel.management.commons.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void format_LocalDate_ShouldReturnFormattedString() {
        LocalDate date = LocalDate.of(2025, 10, 26);
        String result = DateUtils.format(date);
        assertEquals("26/10/2025", result);
    }

    @Test
    void format_NullLocalDate_ShouldReturnEmptyString() {
        String result = DateUtils.format((LocalDate) null);
        assertEquals("", result);
    }

    @Test
    void format_LocalDateTime_ShouldReturnFormattedString() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 10, 26, 17, 45);
        String result = DateUtils.format(dateTime);
        assertEquals("26/10/2025 17:45", result);
    }

    @Test
    void format_NullLocalDateTime_ShouldReturnEmptyString() {
        String result = DateUtils.format((LocalDateTime) null);
        assertEquals("", result);
    }

    @Test
    void isFinalDeSemana_WeekendDate_ShouldReturnTrue() {
        LocalDate sabado = LocalDate.of(2025, 10, 25);
        LocalDate domingo = LocalDate.of(2025, 10, 26);

        assertTrue(DateUtils.isFinalDeSemana(sabado));
        assertTrue(DateUtils.isFinalDeSemana(domingo));
    }

    @Test
    void isFinalDeSemana_WeekdayDate_ShouldReturnFalse() {
        LocalDate segunda = LocalDate.of(2025, 10, 27);
        LocalDate sexta = LocalDate.of(2025, 10, 31);

        assertFalse(DateUtils.isFinalDeSemana(segunda));
        assertFalse(DateUtils.isFinalDeSemana(sexta));
    }

    @Test
    void isFinalDeSemana_WeekendDateTime_ShouldReturnTrue() {
        LocalDateTime sabado = LocalDateTime.of(2025, 10, 26, 23, 59);
        assertTrue(DateUtils.isFinalDeSemana(sabado));
    }
}
