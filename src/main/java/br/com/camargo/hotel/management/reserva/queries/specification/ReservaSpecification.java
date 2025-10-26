package br.com.camargo.hotel.management.reserva.queries.specification;

import br.com.camargo.hotel.management.reserva.domain.entities.Reserva;
import br.com.camargo.hotel.management.reserva.queries.filter.ReservaFiltros;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class ReservaSpecification {

    public static Specification<Reserva> buildSpecification(ReservaFiltros filtros) {
        return (root, query, criteriaBuilder) -> {
            final var predicates = new ArrayList<Predicate>();

            if (filtros.getId().isPresent()) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filtros.getId().get()));
            }

            if (filtros.getHospedeId().isPresent()) {
                predicates.add(criteriaBuilder.equal(root.get("hospede").get("id"), filtros.getHospedeId().get()));
            }

            if (filtros.getEstadiaId().isPresent()) {
                predicates.add(criteriaBuilder.equal(root.get("estadia").get("id"), filtros.getEstadiaId().get()));
            }

            if (filtros.getStatus().isPresent()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filtros.getStatus().get()));
            }

            if (filtros.getAdicionalGaragem().isPresent()) {
                predicates.add(criteriaBuilder.equal(root.get("adicionalGaragem"), filtros.getAdicionalGaragem().get()));
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

    private static Predicate buildDateSpecification(ReservaFiltros filtros, CriteriaBuilder criteriaBuilder, Root<Reserva> root) {
        if (filtros.getDataEntradaPrevista().isPresent() && filtros.getDataSaidaPrevista().isPresent()) {
            return criteriaBuilder.between(root.get("dataEntradaPrevista"), filtros.getDataEntradaPrevista().get(), filtros.getDataSaidaPrevista().get());
        }

        if (filtros.getDataEntradaPrevista().isPresent()) {
            return criteriaBuilder.equal(root.get("dataEntradaPrevista"), filtros.getDataEntradaPrevista().get());
        }

        if (filtros.getDataSaidaPrevista().isPresent()) {
            return criteriaBuilder.equal(root.get("dataSaidaPrevista"), filtros.getDataEntradaPrevista().get());
        }

        return null;
    }
}
