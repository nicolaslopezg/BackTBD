package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "districts")

public class District {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "`name`", unique = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    // Constructor Vacío.
    public District() { }

    // Constructor.
    public District(City city,String name) {
        this.city = city;
        this.name = name;
    }

    // Getter y Setter.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String type) {
        this.name = name;
    }
}