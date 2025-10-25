package br.com.camargo.hotel.management.commons;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonSerialize
public class ResponseVO<T> {
    private String message;
    private T data;
}
