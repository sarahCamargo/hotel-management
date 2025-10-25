package br.com.camargo.hotel.management.hospede.factories;

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
                .documento(entity.getDocumento())
                .telefone(entity.getTelefone())
                .build();
    }

    public Hospede toEntity(HospedeDTO hospedeDTO) {
        return toEntity(null, hospedeDTO);
    }

    public Hospede toEntity(Long id, HospedeDTO hospedeDTO) {
        return Hospede.builder()
                .id(id)
                .nome(hospedeDTO.getNome())
                .documento(hospedeDTO.getDocumento())
                .telefone(hospedeDTO.getTelefone())
                .build();
    }
}
