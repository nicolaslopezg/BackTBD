package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dimensions")

public class Dimension {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "`name`", unique = false)
    private String name;

    // Constructor vacío.
    public Dimension() { }

    // Constructor.
    public Dimension( String name) {
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