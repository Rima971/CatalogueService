package com.swiggy.catalogue.services;

import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.repositories.RestaurantsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.swiggy.catalogue.Constants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        RestaurantRequestDto request = new RestaurantRequestDto();
        request.setName(RESTAURANT_NAME);
        request.setPincode(RESTAURANT_PINCODE);

        assertDoesNotThrow(()-> {
            Restaurant savedRestaurant = this.restaurantsService.create(request);
            assertEquals(this.testRestaurant, savedRestaurant);
        });
        verify(this.mockedRestaurantDao).save(any(Restaurant.class));
    }
}
