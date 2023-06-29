package com.ezra.lender.controller;


import com.ezra.lender.dto.GeneralResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.rmi.ServerException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerAdvice {
    @ExceptionHandler({AccessDeniedException.class, UsernameNotFoundException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GeneralResponse> handledException(
            Exception exception) {
        var response = new GeneralResponse(null,HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<GeneralResponse> handledException(NoSuchElementException exception) {
        var response = new GeneralResponse(null,HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpMediaTypeNotAcceptableException.class,
            HttpMediaTypeNotSupportedException.class, InvalidDataAccessApiUsageException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GeneralResponse> handleHttpExceptions(Exception exception) {
        var response = new GeneralResponse(null,HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoHandlerFoundException.class, DataIntegrityViolationException.class,
            DataIntegrityViolationException.class, IllegalArgumentException.class, ExpiredJwtException.class, NumberFormatException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GeneralResponse> handleInternalServerErrors(Exception exception) {
        var response = new GeneralResponse(null,HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GeneralResponse> handleInternalServerErrors(RuntimeException exception) {
        var response = new GeneralResponse(null,HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({Exception.class, ServerException.class, JwtException.class, io.jsonwebtoken.JwtException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GeneralResponse> handleGeneralExceptions(Exception exception) {
        var response = new GeneralResponse(null,HttpStatus.UNAUTHORIZED,exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
