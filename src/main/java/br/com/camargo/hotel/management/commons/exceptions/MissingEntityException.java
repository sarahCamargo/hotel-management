package br.com.camargo.hotel.management.commons.exceptions;

import lombok.Getter;

@Getter
public class MissingEntityException extends RuntimeException {
    public static final String MSG_ERROR_DEFAULT = "[%s] não encontrado(a) com o id [%s]";

    public MissingEntityException(String className, Long id) {
        super(String.format(MSG_ERROR_DEFAULT, className, id));
    }
}
