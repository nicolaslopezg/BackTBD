package com.example.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "voluntary_dimensions")

public class VoluntaryDimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn
    private Dimension dimension;

    @ManyToOne
    @JoinColumn(name = "voluntary_id")
    private Voluntary voluntary;

    public VoluntaryDimension() {

    }

    public VoluntaryDimension( Dimension dimension, Voluntary voluntary) {
        this.dimension = dimension;
        this.voluntary = voluntary;
    }

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
}