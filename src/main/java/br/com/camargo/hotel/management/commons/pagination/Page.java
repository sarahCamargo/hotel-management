package br.com.camargo.hotel.management.commons.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Page<T> {

    @Builder.Default
    private List<T> content = Collections.emptyList();
    private long numberOfElements;
    private boolean isLastPage;
    private boolean isFirstPage;
    private long totalPages;
    private long totalElements;

    public static <T> Page<T> empty() {
        return Page.<T>builder().build();
    }

    public static <T> Page<T> fromSpringPageable(org.springframework.data.domain.Page<T> page) {
        return Page.<T>builder()
                .content(page.getContent())
                .numberOfElements(page.getNumberOfElements())
                .isLastPage(page.isLast())
                .isFirstPage(page.isFirst())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}
