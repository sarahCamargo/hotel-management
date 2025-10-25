package br.com.camargo.hotel.management.hospede.queries;

import br.com.camargo.hotel.management.commons.Page;
import br.com.camargo.hotel.management.commons.Paginator;
import br.com.camargo.hotel.management.hospede.factories.HospedeFactory;
import br.com.camargo.hotel.management.hospede.repositories.IHospedeRepository;
import br.com.camargo.hotel.management.hospede.structures.viewobjects.HospedeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HospedeQuery implements IHospedeQuery {

    private final IHospedeRepository repository;
    private final HospedeFactory factory;

    @Override
    public Page<HospedeVO> findAll(Paginator paginator) {
        final var page = PageRequest.of(paginator.getPageNumber(), paginator.getPageSize(), Sort.by("id").descending());
        final var result = repository.findAll(null, page);

        final var content = result.getContent().stream()
                .map(factory::toVO)
                .collect(Collectors.toList());

        return Page.<HospedeVO>builder()
                .content(content)
                .numberOfElements(result.getNumberOfElements())
                .isLastPage(result.isLast())
                .isFirstPage(result.isFirst())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();
    }
}
