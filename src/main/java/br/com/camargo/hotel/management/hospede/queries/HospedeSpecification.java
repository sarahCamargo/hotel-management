package br.com.camargo.hotel.management.hospede.queries;

import br.com.camargo.hotel.management.commons.util.SqlUtils;
import br.com.camargo.hotel.management.hospede.domain.entities.Hospede;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class HospedeSpecification {

    public static Specification<Hospede> buildSpecification(HospedeFiltros filtros) {
        return (root, query, criteriaBuilder) -> {
            final var predicates = new ArrayList<Predicate>();

            if (filtros.getNome().isPresent() && StringUtils.isNotBlank(filtros.getNome().get())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.function("unaccent", String.class, criteriaBuilder.upper(root.get("nome"))),
                                SqlUtils.contains(filtros.getNome().get().toUpperCase())
                        )
                );
            }

            if (filtros.getDocumento().isPresent() && StringUtils.isNotBlank(filtros.getDocumento().get())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.upper(root.get("documento")),
                                SqlUtils.contains(filtros.getDocumento().get().toUpperCase())
                        )
                );
            }

            if (filtros.getTelefone().isPresent() && StringUtils.isNotBlank(filtros.getTelefone().get())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.upper(root.get("telefone")),
                                SqlUtils.contains(filtros.getTelefone().get().toUpperCase())
                        )
                );
            }

            if (predicates.isEmpty()) {
                return null;
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
