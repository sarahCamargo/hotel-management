package br.com.camargo.hotel.management.estadia.services;

import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculadoraEstadiaServiceTest {

    @InjectMocks
    private CalculadoraEstadiaService calculadoraEstadiaService;

    @Test
    void calcularValorEstadia_Depois1630EmDiaUtil_ShouldAddDiaUtilTarifa() {
        BigDecimal valorPrevisto = new BigDecimal("500.00");
        LocalDateTime dataHoraSaida = LocalDateTime.of(2025, 10, 17, 17, 0);

        BigDecimal result = calculadoraEstadiaService.calcularValorEstadia(valorPrevisto, dataHoraSaida);

        assertEquals(new BigDecimal("620.00"), result);
    }

    @Test
    void calcularValorEstadia_Depois1630EmFimSemana_ShouldAddFimSemanaTarifa() {
        BigDecimal valorPrevisto = new BigDecimal("500.00");
        LocalDateTime dataHoraSaida = LocalDateTime.of(2025, 10, 26, 17, 0);

        BigDecimal result = calculadoraEstadiaService.calcularValorEstadia(valorPrevisto, dataHoraSaida);

        assertEquals(new BigDecimal("650.00"), result);
    }

    @Test
    void calcularValorEstadia_Antes1630_ShouldReturnOriginalValue() {
        BigDecimal valorPrevisto = new BigDecimal("500.00");
        LocalDateTime dataHoraSaida = LocalDateTime.of(2025, 10, 27, 16, 0);

        BigDecimal result = calculadoraEstadiaService.calcularValorEstadia(valorPrevisto, dataHoraSaida);

        assertEquals(valorPrevisto, result);
    }

    @Test
    void calcularValorEstadia_NullDataSaida_ShouldThrowException() {
        BigDecimal valorPrevisto = new BigDecimal("500.00");

        BusinessException exception = assertThrows(BusinessException.class,
                () -> calculadoraEstadiaService.calcularValorEstadia(valorPrevisto, null));

        assertEquals("Data de saída e valor previsto não podem ser nulos", exception.getMessage());
    }

    @Test
    void calcularValorEstadia_NullValorPrevisto_ShouldThrowException() {
        LocalDateTime dataHoraSaida = LocalDateTime.now();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> calculadoraEstadiaService.calcularValorEstadia(null, dataHoraSaida));

        assertEquals("Data de saída e valor previsto não podem ser nulos", exception.getMessage());
    }
}
