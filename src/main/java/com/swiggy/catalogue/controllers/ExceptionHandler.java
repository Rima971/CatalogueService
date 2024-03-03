package com.swiggy.catalogue.controllers;

import com.swiggy.catalogue.entities.GenericHttpResponse;
import com.swiggy.catalogue.exceptions.DuplicateRestaurantName;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static com.swiggy.catalogue.constants.ErrorMessage.DUPLICATE_RESTAURANT_NAME;
import static com.swiggy.catalogue.constants.ErrorMessage.INVALID_REQUEST;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericHttpResponse> invalidRequest(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        e.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        return GenericHttpResponse.create(HttpStatus.BAD_REQUEST, INVALID_REQUEST, errors);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateRestaurantName.class)
    public ResponseEntity<GenericHttpResponse> duplicateRestaurantName(DuplicateRestaurantName e){
        return GenericHttpResponse.create(HttpStatus.BAD_REQUEST, DUPLICATE_RESTAURANT_NAME, null);
    }

}