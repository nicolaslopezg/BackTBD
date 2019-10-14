package com.example.demo.models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Requirements")
public class Requirement implements Serializable {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, name = "`type`", unique = true)
    private int type;

    @Column(nullable = false, name = "`description`")
    private String description;

    @ManyToOne
    @JoinColumn
    private Task task;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    // Constructor Vacío.
    public Requirement() { }

    // Constructor
    public Requirement( int type, String description) {
        this.type = type;
        this.description = description;
    }

    // Getter y Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) { this.type = type; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}