package br.com.camargo.hotel.management.commons.viewobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FieldErrorVO {
    private String field;
    private String message;
}
