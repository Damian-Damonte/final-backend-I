package com.example.C22C.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.log4j.Logger;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundExceptionHandler (ResourceNotFoundException exception){
        logger.error(exception.getClass().getCanonicalName() + ": " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<String> badRequestExceptionHandler (BadRequestException exception){
        logger.error(exception.getClass().getCanonicalName() + ": " + exception.getMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({PropertyValueException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class, DataIntegrityViolationException.class})
    public ResponseEntity<String> propertyValueExceptionHandler(Exception exception){
        logger.error(exception.getClass().getCanonicalName() + ": " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Atributos incorrectos");
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> invalidFormatExceptionHandler (InvalidFormatException exception){
        logger.error(exception.getClass().getCanonicalName() + ": " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de atributos incorrecto");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> HttpRequestMethodNotSupportedExceptionHandler (HttpRequestMethodNotSupportedException exception, HttpServletRequest request){
        logger.error(exception.getClass().getCanonicalName() + ": " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Path " + request.getRequestURI() + " " + request.getMethod() + " no soportado");
    }

    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public ResponseEntity<String> BadCredentialsExceptionHandler(Exception exception){
        logger.error(exception.getClass().getCanonicalName() + ": " + exception.getMessage());
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception){
        logger.error(exception.getClass().getCanonicalName() + ": " + exception.getMessage());
        return ResponseEntity.internalServerError().body("Error interno del servidor");
    }
}
