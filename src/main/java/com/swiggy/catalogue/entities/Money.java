package com.swiggy.catalogue.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Currency;

@Embeddable
@Data
public class Money {
    double amount;
//    @Enumerated(EnumType.STRING)
    Currency currency;

    public Money(double amount, Currency currency){
        this.currency = currency;
        this.amount = amount;
    }
}
