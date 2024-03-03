package com.swiggy.catalogue.services;

import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.exceptions.DuplicateRestaurantName;
import com.swiggy.catalogue.repositories.RestaurantsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
        when(this.mockedRestaurantDao.existsByName(RESTAURANT_NAME)).thenReturn(true);
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

        assertThrows(DuplicateRestaurantName.class, ()->this.restaurantsService.create(request));
    }

    @Test
    public void test_shouldReturnListOfAllRestaurantsIfPincodeIsNotPassed(){
        this.restaurantsService.fetchAllRestaurants(Optional.empty());

        verify(this.mockedRestaurantDao, times(1)).findAll();
    }

    @Test
    public void test_shouldReturnListOfNearestRestaurantsIfPincodeIsPassed(){
        int pincode = 123456;
        this.restaurantsService.fetchAllRestaurants(Optional.of(pincode));

        verify(this.mockedRestaurantDao, times(1)).findNearestRestaurants(pincode);
    }
}
