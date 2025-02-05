package com.craftorganizer.projectservice.exception;

import com.craftorganizer.projectservice.model.ProjectStatus;
import com.craftorganizer.projectservice.model.ProjectType;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.github.scribejava.core.exceptions.OAuthException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e, WebRequest request) {
        logger.error("Illegal argument exception: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElementException(NoSuchElementException e, WebRequest request) {
        logger.error("Resource not found: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), request);
    }

    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> handleApiRequestException(RestClientException e, WebRequest request) {
        logger.error("API request failed: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(), request);
    }

    @ExceptionHandler(OAuthException.class)
    public ResponseEntity<String> handleOAuthException(OAuthException e, WebRequest request) {
        logger.error("OAuth exception: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, e.getMessage(), request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e, WebRequest request) {
        logger.error("Global exception: {}", e.getMessage());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        logger.error("Validation failed: {}", e.getMessage());

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidEnumJson(HttpMessageNotReadableException e) {
        logger.error("Invalid enum: {}", e.getMessage());
        if (e.getCause() instanceof InvalidFormatException ex && ex.getTargetType().isEnum()) {
            return ResponseEntity.badRequest()
                    .body("Invalid value: " + ex.getValue() + ". Allowed values for Project status: "
                            + Arrays.toString(ProjectStatus.values()) + ". Allowed values for Project type: "
                            + Arrays.toString(ProjectType.values()));
        }
        return ResponseEntity.badRequest().body("Invalid request format.");
    }

    private ResponseEntity buildErrorResponse(HttpStatus status, String message, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}
