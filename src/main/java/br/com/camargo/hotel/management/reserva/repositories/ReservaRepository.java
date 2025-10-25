package br.com.camargo.hotel.management.reserva.repositories;

import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends IReservaRepository, JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {
}
