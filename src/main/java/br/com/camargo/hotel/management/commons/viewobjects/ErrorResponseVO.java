package br.com.camargo.hotel.management.commons.viewobjects;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseVO {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
