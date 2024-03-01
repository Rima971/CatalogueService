package com.swiggy.catalogue.repositories;

import com.swiggy.catalogue.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemDao extends JpaRepository<MenuItem, Integer> {
}
