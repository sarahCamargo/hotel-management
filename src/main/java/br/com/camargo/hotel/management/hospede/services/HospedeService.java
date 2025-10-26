package br.com.camargo.hotel.management.hospede.services;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.viewobjects.ResponseVO;
import br.com.camargo.hotel.management.commons.exceptions.MissingEntityException;
import br.com.camargo.hotel.management.hospede.factories.HospedeFactory;
import br.com.camargo.hotel.management.hospede.queries.filter.HospedeFiltros;
import br.com.camargo.hotel.management.hospede.queries.repository.IHospedeQuery;
import br.com.camargo.hotel.management.hospede.repositories.IHospedeRepository;
import br.com.camargo.hotel.management.hospede.domain.dtos.HospedeDTO;
import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HospedeService {

    private final IHospedeQuery query;
    private final IHospedeRepository repository;
    private final HospedeFactory factory;

    public ResponseEntity<Page<HospedeVO>> visualizarHospedes(HospedeFiltros filtros, Paginator paginator) {
        final Page<HospedeVO> hospedes = query.findAll(filtros, paginator);

        if (hospedes == null || hospedes.getContent().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(hospedes);
    }

    public ResponseEntity<HospedeVO> buscarHospede(Long id) {
        final HospedeVO hospede = query.findById(id);

        if (hospede == null) {
            throw new MissingEntityException("Hóspede", id);
        }

        return ResponseEntity.ok(hospede);
    }

    @Transactional
    public ResponseEntity<ResponseVO<HospedeVO>> cadastrarHospede(HospedeDTO hospedeDTO) {
        final Hospede saved = repository.save(factory.fromDTO(hospedeDTO));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(factory.responseMessage(saved, "Hóspede criado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResponseVO<HospedeVO>> alterarHospede(Long id, HospedeDTO hospedeDTO) {
        final boolean exists = repository.existsById(id);

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        final Hospede saved = repository.save(factory.fromDTO(id, hospedeDTO));

        return ResponseEntity.ok(factory.responseMessage(saved, "Hóspede alterado com sucesso."));
    }

    @Transactional
    public ResponseEntity<ResponseVO<HospedeVO>> removerHospede(Long id) {
        final boolean exists = repository.existsById(id);

        if (!exists) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    public Hospede getHospede(Long id) {
        return repository.findById(id).orElseThrow(() -> new MissingEntityException("Hóspede", id));
    }
}
