package br.com.camargo.hotel.management.estadia.queries.repositories;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.estadia.factories.EstadiaFactory;
import br.com.camargo.hotel.management.estadia.queries.filters.EstadiaFiltros;
import br.com.camargo.hotel.management.estadia.queries.specification.EstadiaSpecification;
import br.com.camargo.hotel.management.estadia.repositories.IEstadiaRepository;
import br.com.camargo.hotel.management.estadia.domain.viewobjects.EstadiaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstadiaQuery implements IEstadiaQuery {

    private final IEstadiaRepository repository;
    private final EstadiaFactory factory;

    @Override
    public Page<EstadiaVO> findAll(EstadiaFiltros filtros, Paginator paginator) {
        final var spec = EstadiaSpecification.buildSpecification(filtros);
        final var page = PageRequest.of(paginator.getPageNumber(), paginator.getPageSize(), Sort.by("id").descending());
        final var result = repository.findAll(spec, page);

        final var content = result.getContent().stream()
                .map(factory::toVO)
                .collect(Collectors.toList());

        return Page.<EstadiaVO>builder()
                .content(content)
                .numberOfElements(result.getNumberOfElements())
                .isLastPage(result.isLast())
                .isFirstPage(result.isFirst())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();
    }
}
