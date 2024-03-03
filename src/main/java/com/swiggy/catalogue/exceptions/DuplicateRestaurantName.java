package com.swiggy.catalogue.exceptions;

import com.sun.jdi.request.DuplicateRequestException;

public class DuplicateRestaurantName extends DuplicateRequestException {
    public DuplicateRestaurantName(){
        super("The given restaurant name already exists.");
    }
}
