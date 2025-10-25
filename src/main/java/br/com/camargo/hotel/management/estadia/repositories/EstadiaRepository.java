package br.com.camargo.hotel.management.estadia.repositories;

import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadiaRepository extends IEstadiaRepository, JpaRepository<Estadia, Long>, JpaSpecificationExecutor<Estadia> {
}
