package org.springtech.classactivity16november.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String,Object>> buildResponse(HttpStatus status, String message){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("Code",status.value());
        map.put("Error",status.getReasonPhrase());
        map.put("Message",message);
        map.put("Timestamp", LocalDateTime.now());
        return ResponseEntity.status(status).body(map);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(
            ResourceNotFoundException exception
    ){
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicates(
            DuplicateResourceException exception
    ){
        return buildResponse(HttpStatus.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException exception
    ){
        Map<String,Object> validationErrors = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    validationErrors.putIfAbsent(
                            error.getField(),
                            error.getDefaultMessage()
                    );
                });

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("Code",HttpStatus.BAD_REQUEST.value());
        map.put("Error",HttpStatus.BAD_REQUEST.getReasonPhrase());
        map.put("Messages",validationErrors);
        map.put("Timestamp",LocalDateTime.now());

        return ResponseEntity.badRequest().body(map);
    }
}
