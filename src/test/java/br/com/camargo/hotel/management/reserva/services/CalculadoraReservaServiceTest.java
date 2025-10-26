package br.com.camargo.hotel.management.reserva.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CalculadoraReservaServiceTest {

    @InjectMocks
    private CalculadoraReservaService calculadoraReservaService;

    @Test
    void calcularValorReserva_DiasUteis_ShouldCalculateCorrectly() {
        LocalDate entrada = LocalDate.of(2025, 10, 27);
        LocalDate saida = LocalDate.of(2025, 10, 30);
        boolean adicionalGaragem = false;

        BigDecimal result = calculadoraReservaService.calcularValorReserva(entrada, saida, adicionalGaragem);

        assertEquals(new BigDecimal("360"), result);
    }

    @Test
    void calcularValorReserva_ComFimSemana_ShouldCalculateCorrectly() {
        LocalDate entrada = LocalDate.of(2025, 10, 31);
        LocalDate saida = LocalDate.of(2025, 11, 3);
        boolean adicionalGaragem = false;

        BigDecimal result = calculadoraReservaService.calcularValorReserva(entrada, saida, adicionalGaragem);

        assertEquals(new BigDecimal("420"), result);
    }

    @Test
    void calcularValorReserva_ComGaragem_ShouldIncludeGarageCost() {
        LocalDate entrada = LocalDate.of(2025, 10, 27);
        LocalDate saida = LocalDate.of(2025, 10, 30);
        boolean adicionalGaragem = true;

        BigDecimal result = calculadoraReservaService.calcularValorReserva(entrada, saida, adicionalGaragem);

        assertEquals(new BigDecimal("405"), result);
    }

    @Test
    void calcularValorReserva_ComGaragemEFimSemana_ShouldCalculateCorrectly() {
        LocalDate entrada = LocalDate.of(2025, 10, 31);
        LocalDate saida = LocalDate.of(2025, 11, 3);
        boolean adicionalGaragem = true;

        BigDecimal result = calculadoraReservaService.calcularValorReserva(entrada, saida, adicionalGaragem);

        assertEquals(new BigDecimal("475"), result);
    }

    @Test
    void calcularValorHospedagem_ShouldCalculateCorrectly() {
        BigDecimal result = calculadoraReservaService.calcularValorHospedagem(3L, 2L);

        assertEquals(new BigDecimal("660"), result);
    }

    @Test
    void calcularValorGaragem_SemGaragem_ShouldReturnZero() {
        BigDecimal result = calculadoraReservaService.calcularValorGaragem(3L, 2L, false);

        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void calcularValorGaragem_ComGaragem_ShouldCalculateCorrectly() {
        BigDecimal result = calculadoraReservaService.calcularValorGaragem(3L, 2L, true);

        assertEquals(new BigDecimal("85"), result);
    }
}
