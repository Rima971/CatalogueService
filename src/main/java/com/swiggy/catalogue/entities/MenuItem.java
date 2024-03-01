package com.swiggy.catalogue.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "menuItems")
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    Money price;
}
