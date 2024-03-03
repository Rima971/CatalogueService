package com.swiggy.catalogue.repositories;

import com.swiggy.catalogue.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantsDao extends JpaRepository<Restaurant, Integer> {
    public boolean existsByName(String name);
    @Query("SELECT id, pincode, name, ABS(?1 - restaurants.pincode) AS delta FROM public.restaurants WHERE ABS(382424-restaurants.pincode) < 10000 ORDER BY delta ASC")
    public List<Restaurant> findNearestRestaurants(int pincode);
}
