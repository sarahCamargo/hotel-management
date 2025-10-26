package br.com.camargo.hotel.management.hospede.queries.specification;

import br.com.camargo.hotel.management.hospede.queries.filter.HospedeFiltros;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class HospedeSpecification {

    public static String buildPredicate(HospedeFiltros filtros) {
        final var predicates = new ArrayList<String>();

        if (filtros.getId().isPresent()) {
            predicates.add("h.id = :id");
        }

        if (filtros.getNome().isPresent() && StringUtils.isNotBlank(filtros.getNome().get())) {
            predicates.add("unnaccent(h.nome) ILIKE  concat('%', :nome, '%')");
        }

        if (filtros.getCpf().isPresent() && StringUtils.isNotBlank(filtros.getCpf().get())) {
            predicates.add("h.cpf ILIKE concat('%', :cpf, '%')");
        }

        if (filtros.getTelefone().isPresent() && StringUtils.isNotBlank(filtros.getTelefone().get())) {
            predicates.add("h.telefone ILIKE concat('%', :telefone, '%')");
        }

        if (predicates.isEmpty()) return "";

        return " WHERE " + predicates.stream()
                .map(x -> "(" + x + ")")
                .collect(Collectors.joining(" AND ")) + " ";
    }
}
