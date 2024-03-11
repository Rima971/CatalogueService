package com.swiggy.catalogue.controllers;

import com.swiggy.catalogue.dtos.GenericHttpResponse;
import com.swiggy.catalogue.exceptions.DuplicateRestaurantName;
import com.swiggy.catalogue.exceptions.InexistentMenuItem;
import com.swiggy.catalogue.exceptions.ItemRestaurantConflictException;
import com.swiggy.catalogue.exceptions.RestaurantNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static com.swiggy.catalogue.constants.ErrorMessage.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericHttpResponse> invalidRequest(MethodArgumentNotValidException e, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();

        e.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        return GenericHttpResponse.create(HttpStatus.BAD_REQUEST, INVALID_REQUEST, errors);
    }

    @ExceptionHandler(DuplicateRestaurantName.class)
    public ResponseEntity<GenericHttpResponse> duplicateRestaurantName(DuplicateRestaurantName e){
        return GenericHttpResponse.create(HttpStatus.BAD_REQUEST, DUPLICATE_RESTAURANT_NAME, null);
    }

    @ExceptionHandler(RestaurantNotFound.class)
    public ResponseEntity<GenericHttpResponse> restaurantNotFound(RestaurantNotFound e){
        return GenericHttpResponse.create(HttpStatus.CONFLICT, RESTAURANT_NOT_FOUND, null);
    }

    @ExceptionHandler(InexistentMenuItem.class)
    public ResponseEntity<GenericHttpResponse> menuItemNotFound(InexistentMenuItem e){
        return GenericHttpResponse.create(HttpStatus.CONFLICT, MENU_ITEM_NOT_FOUND, null);
    }

    @ExceptionHandler(ItemRestaurantConflictException.class)
    public ResponseEntity<GenericHttpResponse> menuItemNotBelongingToGivenRestaurant(ItemRestaurantConflictException e){
        return GenericHttpResponse.create(HttpStatus.CONFLICT, e.getMessage(), null);
    }
}