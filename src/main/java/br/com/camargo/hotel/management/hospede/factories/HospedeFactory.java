package br.com.camargo.hotel.management.hospede.factories;

import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.hospede.domain.dtos.HospedeDTO;
import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;
import org.springframework.stereotype.Component;

@Component
public class HospedeFactory {

    public HospedeVO toVO(Hospede entity) {
        return HospedeVO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .cpf(entity.getCpf())
                .telefone(entity.getTelefone())
                .build();
    }

    public Hospede fromDTO(HospedeDTO hospedeDTO) {
        return fromDTO(null, hospedeDTO);
    }

    public Hospede fromDTO(Long id, HospedeDTO hospedeDTO) {
        return Hospede.builder()
                .id(id)
                .nome(hospedeDTO.getNome())
                .cpf(hospedeDTO.getCpf())
                .telefone(hospedeDTO.getTelefone())
                .build();
    }

    public ResponseVO<HospedeVO> responseMessage(Hospede hospede, String message) {
        return ResponseVO.<HospedeVO>builder()
                .message(message)
                .data(toVO(hospede))
                .build();
    }
}
