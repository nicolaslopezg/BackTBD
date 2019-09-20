package com.example.demo.Models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;
import javax.persistence.*;

@Entity
@Table(name = "voluntary_dimensions")

public class VoluntaryDimension {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NonNull
    private String name;

    @NonNull
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