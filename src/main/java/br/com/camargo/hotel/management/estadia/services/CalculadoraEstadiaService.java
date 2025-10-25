package br.com.camargo.hotel.management.estadia.services;

import br.com.camargo.hotel.management.commons.enums.TipoTarifa;
import br.com.camargo.hotel.management.commons.exceptions.BusinessException;
import br.com.camargo.hotel.management.commons.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class CalculadoraEstadiaService {

    public BigDecimal calcularValorEstadia(BigDecimal valorPrevisto, LocalDateTime dataHoraSaida) {
        if (dataHoraSaida == null || valorPrevisto == null) {
            throw new BusinessException("Data de saída e valor previsto não podem ser nulos");
        }

        final LocalTime limite = LocalTime.of(16, 30);

        if (dataHoraSaida.toLocalTime().isAfter(limite)) {
            if (DateUtils.isFinalDeSemana(dataHoraSaida)) {
                return valorPrevisto.add(TipoTarifa.FIM_SEMANA.getValor());
            }
            return valorPrevisto.add(TipoTarifa.DIA_UTIL.getValor());
        }

        return valorPrevisto;
    }
}
