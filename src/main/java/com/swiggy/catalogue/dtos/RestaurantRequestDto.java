package com.swiggy.catalogue.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantRequestDto {
    @NotNull(message = "name is required")
    @NotBlank(message = "name cannot be blank")
    String name;

    @NotNull(message = "pincode is required")
    @Min(100000)
    int pincode;
}

