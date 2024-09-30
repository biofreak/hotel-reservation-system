package com.example.hotel_reservation_system.handler;

import com.example.hotel_reservation_system.dto.ErrorResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.ServerErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(NotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestHandler(BadRequestException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Неправильный ввод: " + ex.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> badRequestHandler(MethodArgumentNotValidException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Некорректные данные: " +
                ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "[" + fieldError.getDefaultMessage() + "]")
                .collect(Collectors.joining("," + System.lineSeparator()))
        );
    }

    @ExceptionHandler(value = ServerErrorException.class)
    public ResponseEntity<ErrorResponse> ServerErrorHandler(ServerErrorException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus httpStatus, String message) {
        return ResponseEntity.status(httpStatus)
                .body(ErrorResponse.builder()
                        .code(httpStatus.value())
                        .message(message)
                        .build());
    }
}
