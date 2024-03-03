package com.swiggy.catalogue.dtos;

import com.swiggy.catalogue.customValidators.ValueOfEnum;
import com.swiggy.catalogue.enums.Currency;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyDto {
    @Min(value = 0, message = "amount should at least be zero")
    private double amount;

    @NotBlank(message = "currency cannot be null")
    @ValueOfEnum(enumClass = Currency.class)
    private String currency;
}
