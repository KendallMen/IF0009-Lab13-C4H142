package com.medpharm_backend.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<Map<String, String>> handleStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(Map.of("title", "Solicitud no procesada", "detail", ex.getReason() == null ? "Error" : ex.getReason()));
    }
    @ExceptionHandler(Exception.class)
    ResponseEntity<Map<String, String>> handleOther(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("title", "Solicitud invalida", "detail", ex.getMessage() == null ? "Error" : ex.getMessage()));
    }
}
