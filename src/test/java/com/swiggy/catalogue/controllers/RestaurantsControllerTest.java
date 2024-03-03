package com.swiggy.catalogue.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.exceptions.DuplicateRestaurantName;
import com.swiggy.catalogue.services.RestaurantsService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.swiggy.catalogue.Constants.*;
import static com.swiggy.catalogue.constants.ErrorMessage.DUPLICATE_RESTAURANT_NAME;
import static com.swiggy.catalogue.constants.SuccessMessage.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantsService restaurantsService;

    private Restaurant testRestaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_PINCODE);
    private static final String BASE_URL = "/api/restaurants";

    @BeforeEach
    void setUp(){
        reset(this.restaurantsService);
    }

    @Test
    public void test_shouldSuccessfullyCreateRestaurant() throws Exception {
        RestaurantRequestDto request = new RestaurantRequestDto(RESTAURANT_NAME, RESTAURANT_PINCODE);
        String mappedRequest = this.objectMapper.writeValueAsString(request);
        when(this.restaurantsService.create(request)).thenReturn(this.testRestaurant);

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mappedRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(RESTAURANT_SUCCESSFUL_CREATION))
                .andExpect(jsonPath("$.data.id").value(RESTAURANT_ID))
                .andExpect(jsonPath("$.data.name").value(RESTAURANT_NAME))
                .andExpect(jsonPath("$.data.pincode").value(RESTAURANT_PINCODE));

        verify(this.restaurantsService, times(1)).create(request);
    }

    @Test
    public void test_shouldReturn400BadRequestWhenDuplicateRestaurantPassed() throws Exception {
        RestaurantRequestDto request = new RestaurantRequestDto(RESTAURANT_NAME, RESTAURANT_PINCODE);
        String mappedRequest = this.objectMapper.writeValueAsString(request);
        when(this.restaurantsService.create(request)).thenThrow(DuplicateRestaurantName.class);

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mappedRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(DUPLICATE_RESTAURANT_NAME))
                .andExpect(jsonPath("$.data").value(IsNull.nullValue()));
    }

    @Test
    public void test_shouldSuccessfullyFetchAllRestaurantsWhenPincodeNotQueried() throws Exception {
        List<Restaurant> list = new ArrayList<Restaurant>(List.of(new Restaurant(), new Restaurant()));
        when(this.restaurantsService.fetchAllRestaurants(Optional.empty())).thenReturn(list);

        this.mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(SUCCESSFULLY_FETCHED))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").value(hasSize(2)));

        verify(this.restaurantsService, times(1)).fetchAllRestaurants(Optional.empty());
    }

    @Test
    public void test_shouldSuccessfullyFetchARestaurantByItsId() throws Exception {
        when(this.restaurantsService.fetchById(this.testRestaurant.getId())).thenReturn(this.testRestaurant);

        this.mockMvc.perform(get(BASE_URL+"/"+RESTAURANT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(SUCCESSFULLY_FETCHED))
                .andExpect(jsonPath("$.data.id").value(RESTAURANT_ID))
                .andExpect(jsonPath("$.data.name").value(RESTAURANT_NAME))
                .andExpect(jsonPath("$.data.pincode").value(RESTAURANT_PINCODE));

        verify(this.restaurantsService, times(1)).fetchById(RESTAURANT_ID);
    }
}
