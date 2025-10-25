package br.com.camargo.hotel.management.commons.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object... args) {
        super(String.format(message, args));
    }
}
