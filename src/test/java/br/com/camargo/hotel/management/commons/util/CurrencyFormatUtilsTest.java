package br.com.camargo.hotel.management.commons.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyFormatUtilsTest {

    @Test
    void formatar_ValidValue_ShouldReturnFormattedCurrency() {
        BigDecimal value = new BigDecimal("150.50");
        String result = CurrencyFormatUtils.formatar(value);
        assertEquals("R$ 150,50", result);
    }

    @Test
    void formatar_NullValue_ShouldReturnZeroFormatted() {
        String result = CurrencyFormatUtils.formatar(null);
        assertEquals("R$ 0,00", result);
    }

    @Test
    void formatar_ZeroValue_ShouldReturnZeroFormatted() {
        String result = CurrencyFormatUtils.formatar(BigDecimal.ZERO);
        assertEquals("R$ 0,00", result);
    }
}
