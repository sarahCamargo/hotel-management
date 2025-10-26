package br.com.camargo.hotel.management.estadia.queries.specification;

import br.com.camargo.hotel.management.estadia.domain.entities.Estadia;
import br.com.camargo.hotel.management.estadia.queries.filters.EstadiaFiltros;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class EstadiaSpecification {

    public static Specification<Estadia> buildSpecification(EstadiaFiltros filtros) {
        return (root, query, criteriaBuilder) -> {
            final var predicates = new ArrayList<Predicate>();

            if (filtros.getId().isPresent()) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtros.getId().get()));
            }

            if (filtros.getReservaId().isPresent()) {
                predicates.add(criteriaBuilder.equal(root.get("reserva").get("id"), filtros.getReservaId().get()));
            }

            final var dateSpecification = buildDateSpecification(filtros, criteriaBuilder, root);

            if (dateSpecification != null) {
                predicates.add(dateSpecification);
            }

            if (predicates.isEmpty()) {
                return null;
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Predicate buildDateSpecification(EstadiaFiltros filtros, CriteriaBuilder criteriaBuilder, Root<Estadia> root) {
        if (filtros.getDataEntrada().isPresent() && filtros.getDataSaida().isPresent()) {
            LocalDateTime dataHoraEntrada = filtros.getDataEntrada().get().atStartOfDay();
            LocalDateTime dataHoraSaida = filtros.getDataSaida().get().atTime(LocalTime.MAX);
            return criteriaBuilder.between(root.get("dataHoraEntrada"), dataHoraEntrada, dataHoraSaida);
        }

        if (filtros.getDataEntrada().isPresent()) {
            LocalDateTime dataHoraEntradaInicial = filtros.getDataEntrada().get().atStartOfDay();
            LocalDateTime dataHoraEntradaFinal = filtros.getDataEntrada().get().atTime(LocalTime.MAX);
            return criteriaBuilder.between(root.get("dataHoraEntrada"), dataHoraEntradaInicial, dataHoraEntradaFinal);
        }

        if (filtros.getDataSaida().isPresent()) {
            LocalDateTime dataHoraSaidaInicial = filtros.getDataSaida().get().atStartOfDay();
            LocalDateTime dataHoraSaidaFinal = filtros.getDataSaida().get().atTime(LocalTime.MAX);
            return criteriaBuilder.between(root.get("dataHoraSaida"), dataHoraSaidaInicial, dataHoraSaidaFinal);
        }

        return null;
    }
}
