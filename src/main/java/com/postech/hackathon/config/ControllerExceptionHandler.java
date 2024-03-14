package com.postech.hackathon.config;

import com.postech.hackathon.client.exception.ClientException;
import com.postech.hackathon.client.exception.ValidationErrorResponse;
import com.postech.hackathon.optionalService.exception.OfferedServiceException;
import com.postech.hackathon.optionalService.exception.OptionalItemException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleJsonErrors(HttpMessageNotReadableException error) {
        Map<String, String> errorResponse = Map.of("error", error.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<Map<String, String>> handleClientException(ClientException error) {
        return ResponseEntity.status(error.getStatus()).body(Map.of("error", error.getMessage()));
    }

    @ExceptionHandler(OfferedServiceException.class)
    public ResponseEntity<Map<String, String>> handleOfferedServiceException(OfferedServiceException error) {
        return ResponseEntity.status(error.getStatus()).body(Map.of("error", error.getMessage()));
    }

    @ExceptionHandler(OptionalItemException.class)
    public ResponseEntity<Map<String, String>> handleOptionalItemException(OptionalItemException error) {
        return ResponseEntity.status(error.getStatus()).body(Map.of("error", error.getMessage()));
    }
}
