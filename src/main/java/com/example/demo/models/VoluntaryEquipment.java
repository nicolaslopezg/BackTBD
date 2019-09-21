package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="VoluntaryEquipments")
public class VoluntaryEquipment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVoluntaryEquipment")
    private Long idVoluntaryEquipment;


    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "idVoluntary")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Voluntary voluntary;

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "idEquipment")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Equipment equipment;

    public VoluntaryEquipment() {}

    public VoluntaryEquipment( Voluntary vol, Equipment equip) {
        this.voluntary = vol;
        this.equipment = equip;
    }

    public Long getIdVolEquip() {
        return idVoluntaryEquipment;
    }

    public void setIdVolEquip(Long idVolEquip) {
        this.idVoluntaryEquipment = idVolEquip;
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