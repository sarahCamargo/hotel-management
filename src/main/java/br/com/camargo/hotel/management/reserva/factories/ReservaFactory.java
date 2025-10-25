package br.com.camargo.hotel.management.reserva.factories;

import br.com.camargo.hotel.management.commons.util.CurrencyFormatUtils;
import br.com.camargo.hotel.management.commons.util.DateUtils;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import br.com.camargo.hotel.management.reserva.domain.dtos.ReservaDTO;
import br.com.camargo.hotel.management.reserva.domain.enums.StatusReserva;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReservaFactory {

    public ReservaVO toVO(Reserva entity) {
        return ReservaVO.builder()
                .id(entity.getId())
                .hospedeId(entity.getHospede().getId())
                .dataEntradaPrevista(DateUtils.format(entity.getDataEntradaPrevista()))
                .dataSaidaPrevista(DateUtils.format(entity.getDataSaidaPrevista()))
                .valorTotalPrevisto(CurrencyFormatUtils.formatar(entity.getValorTotalPrevisto()))
                .adicionalGaragem(entity.getAdicionalGaragem())
                .status(entity.getStatus().getDescricao())
                .build();
    }

    public Reserva fromDTO(Long id, ReservaDTO reservaDTO, BigDecimal valorReserva, Hospede hospede) {
        return Reserva.builder()
                .id(id)
                .hospede(hospede)
                .dataEntradaPrevista(reservaDTO.getDataEntradaPrevista())
                .dataSaidaPrevista(reservaDTO.getDataSaidaPrevista())
                .valorTotalPrevisto(valorReserva)
                .adicionalGaragem(reservaDTO.getAdicionalGaragem())
                .status(StatusReserva.RESERVADA)
                .build();
    }

    public ResponseVO<ReservaVO> responseMessage(Reserva reserva, String message) {
        return ResponseVO.<ReservaVO>builder()
                .message(message)
                .data(toVO(reserva))
                .build();
    }
}
