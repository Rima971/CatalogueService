package com.swiggy.catalogue.services;

import com.swiggy.catalogue.dtos.MenuItemRequestDto;
import com.swiggy.catalogue.dtos.MoneyDto;
import com.swiggy.catalogue.entities.MenuItem;
import com.swiggy.catalogue.entities.Money;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.enums.Currency;
import com.swiggy.catalogue.exceptions.InexistentMenuItem;
import com.swiggy.catalogue.repositories.MenuItemDao;
import com.swiggy.catalogue.repositories.RestaurantsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.swiggy.catalogue.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class MenuItemsServiceTest {
    @Mock
    private RestaurantsDao mockedRestaurantDao;

    @Mock
    private MenuItemDao mockedMenuItemDao;
    @InjectMocks
    private MenuItemsService menuItemsService;
    private final Restaurant testRestaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_PINCODE);
    private final Money testMenuItemPrice = new Money(0, Currency.INR);
    private final MenuItem testMenuItem = new MenuItem(this.testRestaurant, MENU_ITEM_NAME, this.testMenuItemPrice);
    @BeforeEach
    void setUp() {
        openMocks(this);
        when(this.mockedRestaurantDao.save(any(Restaurant.class))).thenReturn(this.testRestaurant);
        when(this.mockedRestaurantDao.findById(anyInt())).thenReturn(Optional.of(this.testRestaurant));
        when(this.mockedMenuItemDao.save(any(MenuItem.class))).thenReturn(this.testMenuItem);
    }
    @Test
    public void test_shouldCreateAMenuItem(){
        MenuItemRequestDto request = spy(new MenuItemRequestDto(MENU_ITEM_NAME, new MoneyDto(this.testMenuItemPrice.getAmount(), this.testMenuItemPrice.getCurrency().name())));
        when(request.getPrice()).thenReturn(this.testMenuItemPrice);

        assertDoesNotThrow(()-> {
            MenuItem savedMenuItem = this.menuItemsService.create(RESTAURANT_ID, request);
            assertEquals(this.testMenuItem, savedMenuItem);
        });
        verify(this.mockedMenuItemDao).save(any(MenuItem.class));
    }

    @Test
    public void test_shouldFetchAMenuItem(){
        when(this.mockedMenuItemDao.findById(MENU_ITEM_ID)).thenReturn(Optional.of(this.testMenuItem));

        assertDoesNotThrow(()->{
            MenuItem fetchedItem = this.menuItemsService.fetch(MENU_ITEM_ID, RESTAURANT_ID);
            assertEquals(this.testMenuItem, fetchedItem);
        });
        verify(this.mockedMenuItemDao, times(1)).findById(MENU_ITEM_ID);
    }

    @Test
    public void test_shouldThrowInexistentMenuItemExceptionIfInvalidItemIdPassed(){
        when(this.mockedMenuItemDao.findById(MENU_ITEM_ID)).thenReturn(Optional.empty());

        assertThrows(InexistentMenuItem.class, ()->this.menuItemsService.fetch(MENU_ITEM_ID, RESTAURANT_ID));
    }
}
