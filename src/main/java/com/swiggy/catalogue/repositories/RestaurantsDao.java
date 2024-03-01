package com.swiggy.catalogue.repositories;

import com.swiggy.catalogue.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantsDao extends JpaRepository<Restaurant, Integer> {
}
