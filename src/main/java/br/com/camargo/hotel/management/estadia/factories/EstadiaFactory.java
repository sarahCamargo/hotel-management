package br.com.camargo.hotel.management.estadia.factories;

import br.com.camargo.hotel.management.commons.util.CurrencyFormatUtils;
import br.com.camargo.hotel.management.commons.util.DateUtils;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EstadiaFactory {

    public EstadiaVO toVO(Estadia entity) {
        return EstadiaVO.builder()
                .id(entity.getId())
                .reservaId(entity.getReserva().getId())
                .dataEntrada(DateUtils.format(entity.getDataHoraEntrada()))
                .dataSaida(DateUtils.format(entity.getDataHoraSaida()))
                .valorTotalFinal(CurrencyFormatUtils.formatar(entity.getValorTotalFinal()))
                .build();
    }

    public Estadia fromDTO(Reserva reserva) {
        return Estadia.builder()
                .reserva(reserva)
                .dataHoraEntrada(LocalDateTime.now())
                .build();
    }

    public ResponseVO<EstadiaVO> responseMessage(Estadia estadia, String message) {
        return ResponseVO.<EstadiaVO>builder()
                .message(message)
                .data(toVO(estadia))
                .build();
    }
}
