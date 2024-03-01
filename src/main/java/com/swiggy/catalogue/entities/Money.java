package com.swiggy.catalogue.entities;

import com.swiggy.catalogue.enums.Currency;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Embeddable
@Data
public class Money {
    private final double amount;

    @Enumerated(EnumType.STRING)
    private final Currency currency;

    public Money(double amount, Currency currency){
        this.currency = currency;
        this.amount = amount;
    }
}
