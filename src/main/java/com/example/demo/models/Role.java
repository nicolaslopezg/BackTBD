package com.example.demo.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="roles")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    private Long idRole;

    @Column(nullable = false, name = "`tipo`")
    private int tipo;

    @Column(nullable = false, name = "`descripcion`")
    private String descripcion;


    public Role() {

    }

    public Role( int tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setidRole(Long idRole) {
        this.idRole = idRole;
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