package com.swiggy.catalogue.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu_items")
@Data
@NoArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne()
    private Restaurant restaurant;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Money price;

    public MenuItem(Restaurant restaurant, String name, Money price) {
        this.restaurant = restaurant;
        this.name = name;
        this.price = price;
    }
}
