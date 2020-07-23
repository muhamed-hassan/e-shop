package com.cairoshop.web.exceptions;

import static com.cairoshop.configs.Constants.ERROR_KEY;

import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNoResultException(NoResultException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Map.of(ERROR_KEY, exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(ERROR_KEY, exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolatedException(DataIntegrityViolatedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(ERROR_KEY, exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations()
                                    .stream()
                                    .map(ConstraintViolation::getMessage)
                                    .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(ERROR_KEY, message));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                                    .getAllErrors()
                                    .stream()
                                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                    .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(ERROR_KEY, message));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(Map.of(ERROR_KEY, exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception exception) {
        String message = exception.getMessage() == null ? "Unable to process this request." : exception.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of(ERROR_KEY, message));
    }

}
