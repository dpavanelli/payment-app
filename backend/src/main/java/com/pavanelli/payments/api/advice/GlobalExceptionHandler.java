package com.pavanelli.payments.api.advice;

import com.pavanelli.payments.errors.DuplicateEntityException;
import com.pavanelli.payments.errors.InvalidFieldException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<List<Map<String, String>>> handleEntityConflictException(DuplicateEntityException ex) {
        List<Map<String, String>> response = new ArrayList<>();
        Map<String, String> properties = new HashMap<>();
        properties.put("error", ex.getMessage());
        response.add(properties);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<List<Map<String, String>>> handleInvalidFieldException(InvalidFieldException ex) {
        List<Map<String, String>> response = new ArrayList<>();
        Map<String, String> properties = new HashMap<>();
        properties.put("field", ex.getField());
        properties.put("error", ex.getError());
        response.add(properties);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> response = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            Map<String, String> properties = new HashMap<>();
            properties.put("field", error.getField());
            properties.put("error", error.getDefaultMessage());
            response.add(properties);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, String>> response = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Map<String, String> properties = new HashMap<>();
            properties.put("field", violation.getPropertyPath().toString());
            properties.put("error", violation.getMessage());
            response.add(properties);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericExceptions(Exception ex) {
        String traceId = MDC.get("traceId");
        logger.error("[{}] An unexpected error occur: {}", traceId, ex.getMessage(), ex);
        Map<String, String> response = new HashMap<>();
        response.put("message", "An unexpected error occurred. Please, contact the support with the following id: " + traceId);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
