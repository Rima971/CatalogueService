package com.swiggy.catalogue.dtos;

import com.swiggy.catalogue.entities.Money;
import com.swiggy.catalogue.enums.Currency;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemRequestDto {
    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "price is required")
    @Valid
    private MoneyDto price;

    public Money getPrice(){
        return new Money(price.getAmount(), Currency.valueOf(price.getCurrency()));
    }
}
