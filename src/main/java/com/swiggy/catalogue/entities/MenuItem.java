package com.swiggy.catalogue.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menu_items")
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne()
    private Restaurant restaurant;
    private String name;
    private Money price;

    public MenuItem(Restaurant restaurant, String name, Money price) {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
    }
}
