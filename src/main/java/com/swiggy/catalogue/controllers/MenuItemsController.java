package com.swiggy.catalogue.controllers;

import com.swiggy.catalogue.dtos.MenuItemRequestDto;
import com.swiggy.catalogue.dtos.GenericHttpResponse;
import com.swiggy.catalogue.entities.MenuItem;
import com.swiggy.catalogue.services.MenuItemsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.swiggy.catalogue.constants.SuccessMessage.MENU_ITEM_SUCCESSFUL_CREATION;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu-items")
public class MenuItemsController {
    @Autowired
    private MenuItemsService menuItemsService;
    @PostMapping("")
    public ResponseEntity<GenericHttpResponse> create(@PathVariable int restaurantId, @Valid @RequestBody MenuItemRequestDto menuItemRequestDto){
        MenuItem savedMenuItem = this.menuItemsService.create(restaurantId, menuItemRequestDto);
        return GenericHttpResponse.create(HttpStatus.CREATED, MENU_ITEM_SUCCESSFUL_CREATION, savedMenuItem);
    }
}
