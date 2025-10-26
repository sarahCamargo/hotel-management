package br.com.camargo.hotel.management.commons.viewobjects;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ErrorResponseVO {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldErrorVO> errors;
}
