package com.example.adminpaneldemo.exception.handler;

import com.example.adminpaneldemo.dto.response.ExceptionDTO;
import com.example.adminpaneldemo.exception.AlreadyExistException;
import com.example.adminpaneldemo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionDTO handleNotFound(NotFoundException ex) {
        log.error("ActionLog.error not found: {} ", ex.getMessage());
        return new ExceptionDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionDTO handleAlreadyExists(AlreadyExistException ex) {
        log.error("ActionLog.error AlreadyExists: {} ", ex.getMessage());
        return new ExceptionDTO(HttpStatus.CONFLICT.value(), ex.getMessage());
    }
}
