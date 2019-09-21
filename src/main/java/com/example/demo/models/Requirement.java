package com.example.demo.models;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Requirements")
public class Requirement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRequirement")
    private Long idRequirement;

    @Column(nullable = false, name = "`tipo`", unique = true)
    private int tipo;

    @Column(nullable = false, name = "`descripcion`")
    private String descripcion;

    @ManyToOne
    @JoinColumn
    private Task task;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    public Requirement() {

    }

    public Requirement( int tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Long getidRequirement() {
        return idRequirement;
    }

    public void setidRequirement(Long idRequirement) {
        this.idRequirement = idRequirement;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}