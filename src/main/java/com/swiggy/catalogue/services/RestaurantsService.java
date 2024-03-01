package com.swiggy.catalogue.services;

import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.repositories.RestaurantsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantsService {
    @Autowired
    private RestaurantsDao restaurantsDao;
    public Restaurant create(RestaurantRequestDto dto){
        Restaurant restaurant = new Restaurant(dto.getName(), dto.getPincode());
        return this.restaurantsDao.save(restaurant);
    }
}
