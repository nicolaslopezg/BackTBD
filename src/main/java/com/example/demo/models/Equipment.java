package com.example.demo.Models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="equipments")
public class Equipment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEquipment")
    private Long idEquipment;

    @Column(nullable = false, name = "`codigoEquipment`", unique = true)
    private Long codigoEquipment;

    @Column(nullable = false, name = "`tipo`")
    private int tipo;

    @Column(nullable = false, name = "`nombreEquipment`")
    private String nombreEquipment;

    @Column(nullable = false, name = "`certificacion`")
    private String certificacion;

    @Column(nullable = false, name = "`detalle`")
    private String detalle;


    //@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "idCliente")
    //@JsonIgnore
    //private List<Reserva> reservas;

    public Equipment() {

    }

    public Equipment( Long codigoEquipment, int tipo, String nombreEquipment,String certificacion, String detalle) {
        this.codigoEquipment = codigoEquipment;
        this.tipo = tipo;
        this.nombreEquipment = nombreEquipment;
        this.certificacion = certificacion;
        this.detalle = detalle;
    }

    public Long getIdEquipment() {
        return idEquipment;
    }

    public void setidEquipment(Long idEquipment) {
        this.idEquipment = idEquipment;
    }

    public Long getCodigoEquipment() {
        return codigoEquipment;
    }

    public void setCodigoEquipment(Long codigoEquipment) {
        this.codigoEquipment = codigoEquipment;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int rut) {
        this.tipo = tipo;
    }

    public String getNombreEquipment() {
        return nombreEquipment;
    }

    public void setNombreEquipment(String nombreEquipment) {
        this.nombreEquipment = nombreEquipment;
    }

    public String getCertificacion() {
        return certificacion;
    }

    public void setCertificacion(String certificacion) {
        this.certificacion = certificacion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

}