package br.com.camargo.hotel.management.commons.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class CurrencyFormatUtils {

    private static final Locale BRAZIL = Locale.forLanguageTag("pt-BR");

    private static final DecimalFormat BRL_CURRENCY =
            (DecimalFormat) NumberFormat.getCurrencyInstance(BRAZIL);

    public static String formatar(BigDecimal valor) {
        return BRL_CURRENCY.format(Objects.requireNonNullElse(valor, BigDecimal.ZERO));
    }
}
