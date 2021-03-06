package com.microservice.springuserwebservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice     //share across all Controller classes
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {

        ExceptionResponseFormat exceptionResponseFormat = new ExceptionResponseFormat(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponseFormat, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(UserNotFoundException ex, WebRequest request) {

        ExceptionResponseFormat exceptionResponseFormat = new ExceptionResponseFormat(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponseFormat, HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionResponseFormat exceptionResponseFormat = new ExceptionResponseFormat(new Date(), "Validation failed", ex.getBindingResult().toString());

        return new ResponseEntity(exceptionResponseFormat, HttpStatus.BAD_REQUEST);


        //request.getDescription(false) - no error description provided
        //ex.getBindingResult().toString() - detailed error description provided - PostMan - Body
        //instead of using ex.getMessage() can use custom message for example "Validation failed"


    }
}


//Creating Custom Exception and specifying the format of the body
//the specified format and error message would now display in PostMan