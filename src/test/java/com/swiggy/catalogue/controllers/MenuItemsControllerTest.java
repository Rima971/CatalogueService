package com.swiggy.catalogue.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.catalogue.dtos.MenuItemRequestDto;
import com.swiggy.catalogue.dtos.MoneyDto;
import com.swiggy.catalogue.dtos.RestaurantRequestDto;
import com.swiggy.catalogue.entities.MenuItem;
import com.swiggy.catalogue.entities.Money;
import com.swiggy.catalogue.entities.Restaurant;
import com.swiggy.catalogue.enums.Currency;
import com.swiggy.catalogue.services.MenuItemsService;
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
import static com.swiggy.catalogue.constants.SuccessMessage.MENU_ITEM_SUCCESSFUL_CREATION;
import static com.swiggy.catalogue.constants.SuccessMessage.RESTAURANT_SUCCESSFUL_CREATION;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuItemsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MenuItemsService menuItemsService;
    private Restaurant testRestaurant = new Restaurant(RESTAURANT_NAME, RESTAURANT_PINCODE);
    private final Money testMenuItemPrice = new Money(0, Currency.INR);

    private MenuItem testMenuItem = new MenuItem(this.testRestaurant, MENU_ITEM_NAME,this.testMenuItemPrice);
    private static final String BASE_URL = "/api/restaurants/"+RESTAURANT_ID+"/menu-items";
    private MenuItemRequestDto request = new MenuItemRequestDto(MENU_ITEM_NAME, new MoneyDto(0, Currency.INR.name()));

    @BeforeEach
    void setUp(){
        reset(this.menuItemsService);
        when(this.menuItemsService.create(RESTAURANT_ID, this.request)).thenReturn(this.testMenuItem);
    }

    @Test
    public void test_shouldSuccessfullyCreateMenuItem() throws Exception {
        String mappedRequest = this.objectMapper.writeValueAsString(this.request);

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mappedRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(MENU_ITEM_SUCCESSFUL_CREATION))
                .andExpect(jsonPath("$.data.id").value(MENU_ITEM_ID))
                .andExpect(jsonPath("$.data.name").value(MENU_ITEM_NAME))
                .andExpect(jsonPath("$.data.price.amount").value(0))
                .andExpect(jsonPath("$.data.price.currency").value(Currency.INR.name()));

        verify(this.menuItemsService, times(1)).create(RESTAURANT_ID, this.request);
    }
}
