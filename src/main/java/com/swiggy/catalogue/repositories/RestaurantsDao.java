package com.swiggy.catalogue.repositories;

import com.swiggy.catalogue.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantsDao extends JpaRepository<Restaurant, Integer> {
    public boolean existsByName(String name);
    @Query("SELECT id, pincode, name, ABS(pincode - :pincode) AS delta FROM Restaurant WHERE ABS(pincode - :pincode) < 10000 ORDER BY delta ASC")
    public List<Restaurant> findNearestRestaurants(@Param("pincode") int pincode);
}
