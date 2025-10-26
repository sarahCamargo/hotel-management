package br.com.camargo.hotel.management.hospede.repositories;

import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface IHospedeRepository {
    Page<Hospede> findAll(Specification<Hospede> spec, Pageable page);

    Optional<Hospede> findById(Long id);

    Hospede save(Hospede hospede);

    boolean existsById(Long id);

    void deleteById(Long id);

    List<Hospede> findByNomeOrCpfOrTelefone(String nome, String cpf, String telefone);
}
