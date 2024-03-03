package com.swiggy.catalogue.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    int pincode;

    public Restaurant(String name, int pincode){
        this.name = name;
        this.pincode = pincode;
    }
}
