package com.swiggy.catalogue.services;

import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.exceptions.DuplicateRestaurantName;
import com.swiggy.catalogue.exceptions.RestaurantNotFound;
import com.swiggy.catalogue.repositories.RestaurantsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RestaurantsService {
    @Autowired
    private RestaurantsDao restaurantsDao;
    public Restaurant create(RestaurantRequestDto dto) throws DuplicateRestaurantName {
            if (this.restaurantsDao.existsByName(dto.getName())) throw new DuplicateRestaurantName();
            Restaurant restaurant = new Restaurant(dto.getName(), dto.getPincode());
            return this.restaurantsDao.save(restaurant);
    }

    public List<Restaurant> fetchAllRestaurants(Optional<Integer> pincode){
        try{
            return this.restaurantsDao.findNearestRestaurants(pincode.get());
        } catch (NoSuchElementException e){
            return this.restaurantsDao.findAll();
        }
    }

    public Restaurant fetchById(int id) throws RestaurantNotFound {
        return this.restaurantsDao.findById(id).orElseThrow(RestaurantNotFound::new);
    }
}
