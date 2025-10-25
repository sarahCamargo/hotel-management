package br.com.camargo.hotel.management.reserva.queries;

import br.com.camargo.hotel.management.commons.pagination.Page;
import br.com.camargo.hotel.management.commons.pagination.Paginator;
import br.com.camargo.hotel.management.reserva.factories.ReservaFactory;
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
    public Page<ReservaVO> findAll(Paginator paginator) {
        final var page = PageRequest.of(paginator.getPageNumber(), paginator.getPageSize(), Sort.by("id").descending());
        final var result = repository.findAll(null, page);

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
