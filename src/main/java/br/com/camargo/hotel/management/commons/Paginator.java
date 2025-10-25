package br.com.camargo.hotel.management.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Builder
@AllArgsConstructor
public class Paginator {
    private int pageSize;
    private int pageNumber;

    public PageRequest toSpringPageable() {
        return toSpringPageable(null);
    }

    public PageRequest toSpringPageable(Sort sort) {
        return PageRequest.of(pageNumber, pageSize, sort);
    }
}
