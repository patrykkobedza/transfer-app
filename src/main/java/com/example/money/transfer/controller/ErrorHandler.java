package com.example.money.transfer.controller;


import com.example.money.transfer.exception.InsufficientBalanceException;
import com.example.money.transfer.exception.NotFoundException;
import com.example.money.transfer.service.ExchangeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class ErrorHandler {

    Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException exc) {
        logger.warn(exc.getMessage());
        return exc.getMessage();
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleInsufficientBalanceException(InsufficientBalanceException exc) {
        logger.warn(exc.getMessage());
        return exc.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(((FieldError) error).getField() + ": " + error.getDefaultMessage());
        }
        String validationErrorMessage = "Validation Failed " + details.toString();
        logger.warn(validationErrorMessage);

        return validationErrorMessage;
    }

}