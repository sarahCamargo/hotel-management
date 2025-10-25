package br.com.camargo.hotel.management.reserva.services;

import br.com.camargo.hotel.management.commons.enums.TipoTarifa;
import br.com.camargo.hotel.management.commons.util.DateUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class CalculadoraReservaService {

    public BigDecimal calcularValorReserva(LocalDate dataEntradaPrevista, LocalDate dataSaidaPrevista, boolean hasAdicionalGaragem) {
        final long qtdDias = contarDias(dataEntradaPrevista, dataSaidaPrevista);
        final long qtdFinaisSemana = contarFinaisSemana(dataEntradaPrevista, dataSaidaPrevista);
        final long qtdDiasUteis = qtdDias - qtdFinaisSemana;

        final BigDecimal valorHospedagem = calcularValorHospedagem(qtdDiasUteis, qtdFinaisSemana);
        final BigDecimal valorGaragem = calcularValorGaragem(qtdDiasUteis, qtdFinaisSemana, hasAdicionalGaragem);

        return valorHospedagem.add(valorGaragem);
    }

    public Long contarFinaisSemana(LocalDate dataEntrada, LocalDate dataSaida) {
        return dataEntrada
                .datesUntil(dataSaida)
                .filter(DateUtils::isFinalDeSemana)
                .count();
    }

    public Long contarDias(LocalDate dataEntrada, LocalDate dataSaida) {
        return dataEntrada
                .datesUntil(dataSaida)
                .count();
    }

    public BigDecimal calcularValorHospedagem(long diasUteis, long finaisDeSemana) {
        final BigDecimal valorDiasUteis = TipoTarifa.DIA_UTIL.getValor().multiply(BigDecimal.valueOf(diasUteis));
        final BigDecimal valorFinaisDeSemana = TipoTarifa.FIM_SEMANA.getValor().multiply(BigDecimal.valueOf(finaisDeSemana));

        return valorDiasUteis.add(valorFinaisDeSemana);
    }

    public BigDecimal calcularValorGaragem(long diasUteis, long finaisDeSemana, boolean hasAdicionalGaragem) {
        if (!hasAdicionalGaragem) {
            return BigDecimal.ZERO;
        }

        final BigDecimal valorGaragemDiasUteis = TipoTarifa.GARAGEM_DIA_UTIL.getValor().multiply(BigDecimal.valueOf(diasUteis));
        final BigDecimal valorGaragemFinaisDeSemana = TipoTarifa.GARAGEM_FIM_SEMANA.getValor().multiply(BigDecimal.valueOf(finaisDeSemana));

        return valorGaragemDiasUteis.add(valorGaragemFinaisDeSemana);
    }
}
