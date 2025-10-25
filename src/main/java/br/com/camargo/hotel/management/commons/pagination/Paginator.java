package br.com.camargo.hotel.management.commons.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@Builder
@AllArgsConstructor
public class Paginator {
    private Integer pageSize;
    private Integer pageNumber;

    public PageRequest toSpringPageable(Sort sort) {
        return PageRequest.of(getPageNumber(), getPageSize(), sort);
    }

    public int getPageSize() {
        return pageSize == null || pageSize == 0 ? 10 : pageSize;
    }

    public int getPageNumber() {
        return pageNumber == null ? 0 : pageNumber;
    }
}
