package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="VoluntaryEquipments")
public class VoluntaryEquipment implements Serializable {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne
    @JoinColumn(name = "voluntary_id")
    private Voluntary voluntary;

    // Constructor Vacío.
    public VoluntaryEquipment() { }

    // Constructor.
    public VoluntaryEquipment( Voluntary vol, Equipment equip) {
        this.voluntary = vol;
        this.equipment = equip;
    }

    // Getter y Setter.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Voluntary getVoluntary() {
        return voluntary;
    }

    public void setVoluntary(Voluntary voluntary) {
        this.voluntary = voluntary;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

}