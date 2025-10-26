package br.com.camargo.hotel.management.hospede.queries.repository;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.commons.util.SqlUtils;
import br.com.camargo.hotel.management.hospede.domain.viewobjects.HospedeVO;
import br.com.camargo.hotel.management.hospede.queries.projection.HospedeProjection;
import br.com.camargo.hotel.management.hospede.queries.filter.HospedeFiltros;
import br.com.camargo.hotel.management.hospede.queries.specification.HospedeSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class HospedeQuery implements IHospedeQuery {

    private final EntityManager entityManager;

    @Override
    public Page<HospedeVO> findAll(HospedeFiltros filtros, Paginator paginator) {
        final long count = countHospedes(filtros);

        if (count == 0) return Page.empty();

        final var nativeQuery = entityManager.createNativeQuery(getSql(filtros, paginator), HospedeProjection.class);
        bindParameters(filtros, nativeQuery);

        final List<HospedeProjection> projections = nativeQuery.getResultList();

        final List<HospedeVO> hospedes = projections.stream()
                .map(HospedeProjection::toVO)
                .toList();

        return Page.<HospedeVO>builder()
                .content(hospedes)
                .numberOfElements(projections.size())
                .isLastPage(SqlUtils.isLastPage(paginator.getPageNumber(), count, paginator.getPageSize()))
                .isFirstPage(SqlUtils.isFirstPage(paginator.getPageNumber()))
                .totalPages(SqlUtils.totalPages(count, paginator.getPageSize()))
                .totalElements(count)
                .build();
    }

    private long countHospedes(HospedeFiltros filtro) {
        final var nativeQuery = entityManager.createNativeQuery(getCountSql(filtro));

        bindParameters(filtro, nativeQuery);

        return (Long) nativeQuery.getSingleResult();
    }

    @Override
    public HospedeVO findById(Long id) {
        final var filtros = HospedeFiltros.builder().id(id).build();

        final long count = countHospedes(filtros);

        if (count == 0) return null;

        final var nativeQuery = entityManager.createNativeQuery(getSql(filtros, null), HospedeProjection.class);

        bindParameters(filtros, nativeQuery);

        final var result = nativeQuery.getSingleResult();

        return ((HospedeProjection) result).toVO();
    }

    private String getSql(HospedeFiltros filtro, Paginator paginator) {
        return HospedeQueries.SELECT + HospedeQueries.JOIN + HospedeSpecification.buildPredicate(filtro) + HospedeQueries.GROUP_BY + getOffsetAndLimit(paginator);
    }

    private String getCountSql(HospedeFiltros filtro) {
        return HospedeQueries.COUNT + HospedeQueries.JOIN + HospedeSpecification.buildPredicate(filtro);
    }

    private String getOffsetAndLimit(Paginator paginator) {
        if (paginator == null) return "";

        final var offset = paginator.getPageNumber() * paginator.getPageSize();
        return String.format(HospedeQueries.OFFSET_LIMIT, offset, paginator.getPageSize());
    }

    private void bindParameters(HospedeFiltros filtro, Query query) {
        if (filtro.getId().isPresent()) {
            query.setParameter("id", filtro.getId().get());
        }

        if (filtro.getNome().isPresent()) {
            query.setParameter("nome", filtro.getNome().get());
        }

        if (filtro.getCpf().isPresent()) {
            query.setParameter("cpf", filtro.getCpf().get());
        }

        if (filtro.getTelefone().isPresent()) {
            query.setParameter("telefone", filtro.getTelefone().get());
        }
    }
}
