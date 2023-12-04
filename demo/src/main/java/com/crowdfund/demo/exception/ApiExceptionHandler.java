package com.crowdfund.demo.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.*;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus);
        return new ResponseEntity<>(apiException, httpStatus);
    }
}
