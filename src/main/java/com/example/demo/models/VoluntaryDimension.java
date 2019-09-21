package com.example.demo.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "voluntary_dimensions")

public class VoluntaryDimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "`name`", unique = true)
    private String name;

    @NotNull
    @Column(nullable = false, name = "`quantity`", unique = true)
    private Integer quantity;

    @ManyToOne
    @JoinColumn
    private Dimension dimension;
    /*
    @ManyToOne
    @JoinColumn
    private Voluntary voluntary;*/

    // Faltan las llaves foraneas.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}