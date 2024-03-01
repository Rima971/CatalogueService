package com.swiggy.catalogue.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "restaurants")
@Data
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int pincode;

    public Restaurant(String name, int pincode){
        this.name = name;
        this.pincode = pincode;
    }
}
