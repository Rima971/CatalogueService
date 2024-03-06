package com.swiggy.catalogue.repositories;

import com.swiggy.catalogue.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantsDao extends JpaRepository<Restaurant, Integer> {
    public boolean existsByName(String name);
    @Query("SELECT r FROM Restaurant r WHERE ABS(r.pincode - :pincode) < 10000 ORDER BY ABS(r.pincode - :pincode) ASC")
    public List<Restaurant> findAllByPincode(@Param("pincode") int pincode);
}
