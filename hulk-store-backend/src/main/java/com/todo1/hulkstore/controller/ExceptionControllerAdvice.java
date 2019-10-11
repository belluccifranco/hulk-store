package com.todo1.hulkstore.controller;

import com.todo1.hulkstore.exceptions.BusinessServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvice {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleConstraintViolationException(ConstraintViolationException ex) {
    String message =
        ex.getConstraintViolations().stream()
            .map(v -> v.getPropertyPath() + " " + v.getMessage() + "\n")
            .collect(Collectors.joining());
    logger.error(message, ex);
    return message;
  }

  @ExceptionHandler(BusinessServiceException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleServiceException(BusinessServiceException ex) {
    logger.error(ex.getMessage(), ex);
    return ex.getMessage();
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleException(Exception ex) {
    logger.error(ex.getMessage(), ex);
    return "Sorry, an unexpected error occurred";
  }
}
