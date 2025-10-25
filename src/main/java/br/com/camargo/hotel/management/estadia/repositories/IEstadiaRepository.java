package br.com.camargo.hotel.management.estadia.repositories;

import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface IEstadiaRepository {

    Page<Estadia> findAll(Specification<Estadia> spec, Pageable page);

    Optional<Estadia> findById(Long id);

    Estadia save(Estadia hospede);

    boolean existsByReserva_IdAndDataHoraSaidaIsNull(Long reservaId);

    boolean existsById(Long id);

    void deleteById(Long id);
}
