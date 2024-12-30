package com.workintech.s18d1.exceptions;

import com.workintech.s18d1.entity.Burger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BurgerException.class)
    private ResponseEntity<BurgerErrorResponse> handleException(BurgerException burgerException){
         log.error("burger exception occured! Exception details: ",burgerException);
         BurgerErrorResponse burgerErrorResponse=new BurgerErrorResponse(burgerException.getMessage());
         return new ResponseEntity<>(burgerErrorResponse,burgerException.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<BurgerErrorResponse> handleException(Exception exception){
        log.error("Exception occured! Exception details: ",exception);
        BurgerErrorResponse burgerErrorResponse=new BurgerErrorResponse(exception.getMessage());
        return new ResponseEntity<>(burgerErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR );
    }

}
