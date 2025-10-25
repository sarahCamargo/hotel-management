package br.com.camargo.hotel.management.hospede.repositories;

import br.com.camargo.hotel.management.hospede.structures.persistence.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HospedeRepository extends IHospedeRepository, JpaRepository<Hospede, Long>, JpaSpecificationExecutor<Hospede> {
}
