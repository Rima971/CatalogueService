package com.swiggy.catalogue.services;

import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.exceptions.DuplicateRestaurantName;
import com.swiggy.catalogue.exceptions.RestaurantNotFound;
import com.swiggy.catalogue.repositories.RestaurantsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.swiggy.catalogue.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class RestaurantsServiceTest {
    @Mock
    private RestaurantsDao mockedRestaurantDao;
    @InjectMocks
    private RestaurantsService restaurantsService;
    private Restaurant testRestaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_PINCODE);
    @BeforeEach
    void setUp() {
        openMocks(this);
        when(this.mockedRestaurantDao.save(any(Restaurant.class))).thenReturn(this.testRestaurant);
    }
    @Test
    public void test_shouldCreateARestaurant(){
        RestaurantRequestDto request = new RestaurantRequestDto(RESTAURANT_NAME, RESTAURANT_PINCODE);

        assertDoesNotThrow(()-> {
            Restaurant savedRestaurant = this.restaurantsService.create(request);
            assertEquals(this.testRestaurant, savedRestaurant);
        });

        verify(this.mockedRestaurantDao).save(any(Restaurant.class));
    }

    @Test
    public void test_shouldThrowDuplicateRestaurantExceptionWhenTheRestaurantNameBeingUsedForCreationAlreadyExists(){
        RestaurantRequestDto request = new RestaurantRequestDto(RESTAURANT_NAME, RESTAURANT_PINCODE);
        when(this.mockedRestaurantDao.existsByName(RESTAURANT_NAME)).thenReturn(true);

        assertThrows(DuplicateRestaurantName.class, ()->this.restaurantsService.create(request));
    }

    @Test
    public void test_shouldReturnListOfAllRestaurantsIfPincodeIsNotPassed(){
        List<Restaurant> list = new ArrayList<Restaurant>(List.of(new Restaurant(), new Restaurant(), new Restaurant()));
        when(this.mockedRestaurantDao.findAll()).thenReturn(list);
        List<Restaurant> returnedList = this.restaurantsService.fetchAllRestaurants(Optional.empty());

        verify(this.mockedRestaurantDao, times(1)).findAll();
        assertEquals(list, returnedList);
    }

    @Test
    public void test_shouldReturnListOfNearestRestaurantsIfPincodeIsPassed(){
        int pincode = 123456;
        List<Restaurant> list = new ArrayList<Restaurant>(List.of(new Restaurant(), new Restaurant(), new Restaurant()));
        when(this.mockedRestaurantDao.findAllByPincode(pincode)).thenReturn(list);

        List<Restaurant> returnedList = this.restaurantsService.fetchAllRestaurants(Optional.of(pincode));

        verify(this.mockedRestaurantDao, times(1)).findAllByPincode(pincode);
        assertEquals(list, returnedList);
    }

    @Test
    public void test_shouldFetchARestaurantByItsId(){
        when(this.mockedRestaurantDao.findById(RESTAURANT_ID)).thenReturn(Optional.ofNullable(this.testRestaurant));

        assertDoesNotThrow(()-> {
            Restaurant foundRestaurant = this.restaurantsService.fetchById(RESTAURANT_ID);
            assertEquals(this.testRestaurant, foundRestaurant);
        });
        verify(this.mockedRestaurantDao, times(1)).findById(RESTAURANT_ID);
    }

    @Test
    public void test_shouldThrowRestaurantNotFoundException(){
        when(this.mockedRestaurantDao.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFound.class, ()-> this.restaurantsService.fetchById(RESTAURANT_ID));
    }
}
