package com.example.demo.models;



import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="volunteers")
public class Voluntary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVoluntary")
    private Long idVoluntary;

    @Column(nullable = false, name = "`rut`", unique = true)
    private int rut;

    @Column(nullable = false, name = "`nombre`")
    private String nombre;

    @Column(nullable = false, name = "`apellido`")
    private String apellido;

    @Column(nullable = false, name = "`correo`")
    private String correo;

    @Column(nullable = false, name = "`fechaNacimiento`")
    private Date fechaNacimiento;

    @Column(nullable = false, name = "`asignado`")
    private Boolean asignado;

    //@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "idCliente")
    //@JsonIgnore
    //private List<Reserva> reservas;

    public Voluntary() {

    }

    public Voluntary( int rut, String nombre,String apellido, String correo ,Date fechaNacimiento, Boolean asignado) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.asignado = asignado;
    }

    public Long getIdVoluntary() {
        return idVoluntary;
    }

    public void setidVoluntary(Long idUsuario) {
        this.idVoluntary = idVoluntary;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean getAsignado() {
        return asignado;
    }

    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }

}