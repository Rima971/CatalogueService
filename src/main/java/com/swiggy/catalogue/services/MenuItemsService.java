package com.swiggy.catalogue.services;

import com.swiggy.catalogue.dtos.MenuItemRequestDto;
import com.swiggy.catalogue.entities.MenuItem;
import com.swiggy.catalogue.entities.Money;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.repositories.MenuItemDao;
import com.swiggy.catalogue.repositories.RestaurantsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemsService {
    @Autowired
    private MenuItemDao menuItemDao;

    @Autowired
    private RestaurantsDao restaurantsDao;
    public MenuItem create(int restaurantId, MenuItemRequestDto dto){
        Restaurant restaurant = this.restaurantsDao.findById(restaurantId).orElseThrow();
        MenuItem item = new MenuItem(restaurant, dto.getName(), dto.getPrice());
        return this.menuItemDao.save(item);
    }


}
