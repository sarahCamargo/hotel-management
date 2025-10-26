package br.com.camargo.hotel.management.reserva.repositories;

import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReservaRepository extends IReservaRepository, JpaRepository<Reserva, Long>, JpaSpecificationExecutor<Reserva> {

    @Query("""
                SELECT r FROM Reserva r
                WHERE r.hospede.id = :hospedeId
                  AND r.status <> 'CANCELADA'
                  AND (
                       (:entrada BETWEEN r.dataEntradaPrevista AND r.dataSaidaPrevista)
                    OR (:saida BETWEEN r.dataEntradaPrevista AND r.dataSaidaPrevista)
                    OR (r.dataEntradaPrevista BETWEEN :entrada AND :saida)
                  )
            """)
    Optional<Reserva> findByHospedeIdAndPeriodo(
            @Param("hospedeId") Long hospedeId,
            @Param("entrada") LocalDate entrada,
            @Param("saida") LocalDate saida
    );

    @Query("SELECT r FROM Reserva r " +
            "WHERE r.hospede.id = :hospedeId " +
            "AND r.status = 'RESERVADA'")
    @Override
    Optional<Reserva> findReservaAtivaPorHospede(@Param("hospedeId") Long hospedeId);
}
