package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "voluntary_dimensions")

public class VoluntaryDimension {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, name = "`quantity`")
    int  quantity;

    @ManyToOne
    @JoinColumn(name = "dimension_id")
    private Dimension dimension;

    @ManyToOne
    @JoinColumn(name = "voluntary_id")
    private Voluntary voluntary;

    // Constructor Vacío.
    public VoluntaryDimension() { }

    // Constructor.
    public VoluntaryDimension( Dimension dimension, Voluntary voluntary, int quantity) {
        this.dimension = dimension;
        this.voluntary = voluntary;
        this.quantity = quantity;
    }

    // Getter y Setter.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Voluntary getVoluntary() {
        return voluntary;
    }

    public void setVoluntary(Voluntary voluntary) {
        this.voluntary = voluntary;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}