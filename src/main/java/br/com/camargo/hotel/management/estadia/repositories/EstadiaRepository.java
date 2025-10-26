package br.com.camargo.hotel.management.estadia.repositories;

import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadiaRepository extends IEstadiaRepository, JpaRepository<Estadia, Long>, JpaSpecificationExecutor<Estadia> {

    @Query("SELECT e FROM Estadia e " +
            "JOIN e.reserva r " +
            "JOIN r.hospede h " +
            "WHERE e.dataHoraSaida IS NULL " +
            "AND (:nome IS NULL OR h.nome LIKE %:nome%) " +
            "AND (:cpf IS NULL OR h.cpf = :cpf) " +
            "AND (:telefone IS NULL OR h.telefone = :telefone)")
    @Override
    List<Estadia> findEstadiaAtivaPorHospede(@Param("nome") String nome,
                                             @Param("cpf") String cpf,
                                             @Param("telefone") String telefone);
}
