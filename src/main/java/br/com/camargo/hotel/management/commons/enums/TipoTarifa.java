package br.com.camargo.hotel.management.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum TipoTarifa {
    DIA_UTIL(new BigDecimal("120")),
    FIM_SEMANA(new BigDecimal("150")),
    GARAGEM_DIA_UTIL(new BigDecimal("15")),
    GARAGEM_FIM_SEMANA(new BigDecimal("20"));

    private final BigDecimal valor;
}
