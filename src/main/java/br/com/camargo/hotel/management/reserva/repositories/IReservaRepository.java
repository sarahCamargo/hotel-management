package br.com.camargo.hotel.management.reserva.repositories;

import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Optional;

public interface IReservaRepository {

    Page<Reserva> findAll(Specification<Reserva> spec, Pageable page);

    Optional<Reserva> findById(Long id);

    Reserva save(Reserva hospede);

    boolean existsById(Long id);

    void deleteById(Long id);

    Optional<Reserva> findByHospedeIdAndPeriodo(Long hospedeId, LocalDate entrada, LocalDate saida);
}
