package br.com.camargo.hotel.management.commons.exceptions;

import br.com.camargo.hotel.management.commons.util.DateUtils;
import br.com.camargo.hotel.management.commons.viewobjects.ErrorResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingEntityException.class)
    public ResponseEntity<ErrorResponseVO> handleMissingEntity(MissingEntityException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseVO> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseVO> handleValidationException(MethodArgumentNotValidException ex,
                                                                     HttpServletRequest request) {
        final Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return buildError(HttpStatus.BAD_REQUEST, "Erro de validação nos campos: " + errors, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseVO> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor. " + ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponseVO> buildError(HttpStatus status, String message, HttpServletRequest request) {
        ErrorResponseVO error = ErrorResponseVO.builder()
                .timestamp(DateUtils.format(LocalDateTime.now()))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(error);
    }
}
