package com.swiggy.catalogue.exceptions;

import com.sun.jdi.request.DuplicateRequestException;

import static com.swiggy.catalogue.constants.ErrorMessage.DUPLICATE_RESTAURANT_NAME;

public class DuplicateRestaurantName extends DuplicateRequestException {
    public DuplicateRestaurantName(){
        super(DUPLICATE_RESTAURANT_NAME);
    }
}
