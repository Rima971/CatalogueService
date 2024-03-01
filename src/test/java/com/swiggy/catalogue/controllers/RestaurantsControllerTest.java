package com.swiggy.catalogue.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.services.RestaurantsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.swiggy.catalogue.Constants.*;
import static com.swiggy.catalogue.constants.SuccessMessage.*;
import static org.mockito.Mockito.*;
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
        RestaurantRequestDto request = new RestaurantRequestDto();
        request.setName(RESTAURANT_NAME);
        request.setPincode(RESTAURANT_PINCODE);
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
}
