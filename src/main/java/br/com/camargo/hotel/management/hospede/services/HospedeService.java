package br.com.camargo.hotel.management.hospede.services;

import br.com.camargo.hotel.management.commons.Page;
import br.com.camargo.hotel.management.commons.Paginator;
import br.com.camargo.hotel.management.commons.ResponseVO;
import br.com.camargo.hotel.management.hospede.factories.HospedeFactory;
import br.com.camargo.hotel.management.hospede.queries.IHospedeQuery;
import br.com.camargo.hotel.management.hospede.repositories.IHospedeRepository;
import br.com.camargo.hotel.management.hospede.structures.dtos.HospedeDTO;
import br.com.camargo.hotel.management.hospede.structures.persistence.Hospede;
import br.com.camargo.hotel.management.hospede.structures.viewobjects.HospedeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class HospedeService {

    private final IHospedeQuery query;
    private final IHospedeRepository repository;
    private final HospedeFactory factory;

    public ResponseEntity<Page<HospedeVO>> findAll(Paginator paginator) {
        final Page<HospedeVO> hospedes = query.findAll(paginator);

        if (hospedes == null || hospedes.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(hospedes);
    }

    public ResponseEntity<HospedeVO> findById(Long id) {
        final Optional<Hospede> hospede = repository.findById(id);

        if (hospede.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(hospede.map(factory::toVO).orElse(null));
    }

    public ResponseEntity<ResponseVO<HospedeVO>> create(HospedeDTO hospedeDTO) {
        if (hospedeDTO == null) {
            return null;
        }

        final Hospede saved = repository.save(factory.toEntity(hospedeDTO));

        return ResponseEntity.ok(
                ResponseVO.<HospedeVO>builder()
                        .message("Hóspede criado com sucesso.")
                        .data(factory.toVO(saved))
                        .build());
    }

    public ResponseEntity<ResponseVO<HospedeVO>> update(Long id, HospedeDTO hospedeDTO) {
        final boolean exists = repository.existsById(id);

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        final Hospede saved = repository.save(factory.toEntity(id, hospedeDTO));

        return ResponseEntity.ok(
                ResponseVO.<HospedeVO>builder()
                        .message("Hóspede alterado com sucesso.")
                        .data(factory.toVO(saved))
                        .build());
    }

    public ResponseEntity<ResponseVO<HospedeVO>> delete(Long id) {
        final boolean exists = repository.existsById(id);

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.ok(
                ResponseVO.<HospedeVO>builder()
                        .message("Hóspede [" + id + "] excluido com sucesso.")
                        .build());
    }
}
