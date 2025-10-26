package br.com.camargo.hotel.management.hospede.repositories;

import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospedeRepository extends IHospedeRepository, JpaRepository<Hospede, Long>, JpaSpecificationExecutor<Hospede> {

    @Query(value = "SELECT DISTINCT ON (h.id) h.* " +
            "FROM hospede h " +
            "JOIN reserva r ON r.hospede_id = h.id " +
            "JOIN estadia e ON e.reserva_id = r.id " +
            "WHERE e.data_hora_saida IS NOT NULL " +
            "ORDER BY h.id, e.data_hora_saida DESC", nativeQuery = true)
    @Override
    List<Hospede> findHospedesCheckOut();

    @Query("SELECT DISTINCT h FROM Hospede h " +
            "JOIN h.reservas r " +
            "JOIN r.estadia e " +
            "WHERE e.dataHoraSaida IS NULL")
    @Override
    List<Hospede> findHospedesAtivos();
}
