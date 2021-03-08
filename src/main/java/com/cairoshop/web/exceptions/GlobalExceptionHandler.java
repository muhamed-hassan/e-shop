package com.cairoshop.web.exceptions;

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
    public ResponseEntity<Error> handleNoResultException(NoResultException exception) {
        return responseFrom(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleIllegalArgumentException(IllegalArgumentException exception) {
        return responseFrom(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleDataIntegrityViolatedException(DataIntegrityViolatedException exception) {
        return responseFrom(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException exception) {
        var message = exception.getConstraintViolations()
                                        .stream()
                                        .map(ConstraintViolation::getMessage)
                                        .collect(Collectors.joining(", "));
        return responseFrom(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        var message = exception.getBindingResult()
                                        .getAllErrors()
                                        .stream()
                                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                        .collect(Collectors.joining(", "));
        return responseFrom(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleAccessDeniedException(AccessDeniedException exception) {
        return responseFrom(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Error> handleGeneralException(Exception exception) {
        var message = exception.getMessage() == null ? "Unable to process this request." : exception.getMessage();
        return responseFrom(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    private ResponseEntity<Error> responseFrom(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus)
                             .body(new Error(message));
    }

}
