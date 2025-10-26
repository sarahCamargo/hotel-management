package br.com.camargo.hotel.management.commons.exceptions;

import br.com.camargo.hotel.management.commons.util.DateUtils;
import br.com.camargo.hotel.management.commons.viewobjects.ErrorResponseVO;
import br.com.camargo.hotel.management.commons.viewobjects.FieldErrorVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingEntityException.class)
    public ResponseEntity<ErrorResponseVO> handleMissingEntity(MissingEntityException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseVO> handleIllegalArgument(BusinessException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseVO> handleValidationException(MethodArgumentNotValidException ex,
                                                                     HttpServletRequest request) {
        List<FieldErrorVO> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorVO(error.getField(), error.getDefaultMessage()))
                .toList();

        return buildError(HttpStatus.BAD_REQUEST, "Erro de validação nos campos", fieldErrors, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseVO> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor. " + ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponseVO> buildError(HttpStatus status, String message, HttpServletRequest request) {
        return buildError(status, message, null, request);
    }

    private ResponseEntity<ErrorResponseVO> buildError(HttpStatus status, String message, List<FieldErrorVO> errors, HttpServletRequest request) {
        ErrorResponseVO error = ErrorResponseVO.builder()
                .timestamp(DateUtils.format(LocalDateTime.now()))
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getRequestURI())
                .errors(errors)
                .build();

        return ResponseEntity.status(status).body(error);
    }
}
