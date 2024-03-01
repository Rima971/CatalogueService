package com.swiggy.catalogue.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequestDto {
    @NotNull(message = "name is required")
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "pincode is required")
    @Min(100000)
    private int pincode;
}

