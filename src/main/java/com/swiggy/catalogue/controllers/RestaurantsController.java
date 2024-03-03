package com.swiggy.catalogue.controllers;

import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.GenericHttpResponse;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.services.RestaurantsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.swiggy.catalogue.constants.SuccessMessage.*;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantsController {
    @Autowired
    private RestaurantsService restaurantsService;
    @PostMapping("")
    public ResponseEntity<GenericHttpResponse> create(@Valid @RequestBody RestaurantRequestDto restaurantRequestDto){
        Restaurant savedRestaurant = this.restaurantsService.create(restaurantRequestDto);
        return GenericHttpResponse.create(HttpStatus.CREATED, RESTAURANT_SUCCESSFUL_CREATION, savedRestaurant);
    }

    @GetMapping("")
    public ResponseEntity<GenericHttpResponse> fetchAll(@RequestParam(required = false) Optional<Integer> pincode){
        List<Restaurant> returnedList = this.restaurantsService.fetchAllRestaurants(pincode);
        return GenericHttpResponse.create(HttpStatus.OK, SUCCESSFULLY_FETCHED, returnedList);
    }
}
