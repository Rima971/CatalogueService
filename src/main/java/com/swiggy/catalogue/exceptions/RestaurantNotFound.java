package com.swiggy.catalogue.exceptions;

import static com.swiggy.catalogue.constants.ErrorMessage.RESTAURANT_NOT_FOUND;

public class RestaurantNotFound extends RuntimeException {
    public RestaurantNotFound(){
        super(RESTAURANT_NOT_FOUND);
    }
}
