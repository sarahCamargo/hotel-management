package br.com.camargo.hotel.management.reserva.queries.repositories;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.reserva.factories.ReservaFactory;
import br.com.camargo.hotel.management.reserva.queries.filter.ReservaFiltros;
import br.com.camargo.hotel.management.reserva.queries.specification.ReservaSpecification;
import br.com.camargo.hotel.management.reserva.repositories.IReservaRepository;
import br.com.camargo.hotel.management.reserva.domain.viewobjects.ReservaVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservaQuery implements IReservaQuery {

    private final IReservaRepository repository;
    private final ReservaFactory factory;

    @Override
    public Page<ReservaVO> findAll(ReservaFiltros filtros, Paginator paginator) {
        final var spec = ReservaSpecification.buildSpecification(filtros);
        final var page = PageRequest.of(paginator.getPageNumber(), paginator.getPageSize(), Sort.by("id").descending());
        final var result = repository.findAll(spec, page);

        final var content = result.getContent().stream()
                .map(factory::toVO)
                .collect(Collectors.toList());

        return Page.<ReservaVO>builder()
                .content(content)
                .numberOfElements(result.getNumberOfElements())
                .isLastPage(result.isLast())
                .isFirstPage(result.isFirst())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();
    }
}
