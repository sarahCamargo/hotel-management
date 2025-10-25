package br.com.camargo.hotel.management.reserva.factories;

import br.com.camargo.hotel.management.commons.util.CurrencyFormatUtils;
import br.com.camargo.hotel.management.commons.util.DateFormatUtils;
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
                .dataEntradaPrevista(DateFormatUtils.format(entity.getDataEntradaPrevista()))
                .dataSaidaPrevista(DateFormatUtils.format(entity.getDataSaidaPrevista()))
                .valorTotalPrevisto(CurrencyFormatUtils.formatar(entity.getValorTotalPrevisto()))
                .adicionalGaragem(entity.getAdicionalGaragem())
                .status(entity.getStatus().getDescricao())
                .build();
    }

    public Reserva toEntity(ReservaDTO reservaDTO, BigDecimal valorReserva) {
        return toEntity(null, valorReserva, reservaDTO);
    }

    public Reserva toEntity(Long id, BigDecimal valorReserva, ReservaDTO reservaDTO) {
        return Reserva.builder()
                .id(id)
                .hospede(new Hospede(reservaDTO.getHospedeId()))
                .dataEntradaPrevista(reservaDTO.getDataEntradaPrevista())
                .dataSaidaPrevista(reservaDTO.getDataSaidaPrevista())
                .valorTotalPrevisto(valorReserva)
                .adicionalGaragem(reservaDTO.getAdicionalGaragem())
                .status(StatusReserva.RESERVADA)
                .build();
    }
}
