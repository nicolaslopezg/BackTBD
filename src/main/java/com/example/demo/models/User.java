package com.example.demo.models;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.demo.models.Role;

//kill -9 $(lsof -t -i:8080)
@Entity
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long idUser;

    @Column(nullable = false, name = "`rut`", unique = true)
    private int rut;

    @Column(nullable = false, name = "`nombreUser`")
    private String nombreUser;

    @Column(nullable = false, name = "`apellidoUser`")
    private String apellidoUser;

    @Column(nullable = false, name = "`correoUser`")
    private String correoUser;

    @Column(nullable = false, name = "`fechaNacimiento`")
    private Date fechaNacimiento;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    public User() {

    }

    //Constructor para voluntarios leidos desde el csv.
    public User(int rut, String nombreUser, String apellidoUser, String correoUser,Role role) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.rut = rut;
        this.nombreUser = nombreUser;
        this.apellidoUser = apellidoUser;
        this.correoUser = correoUser;
        this.fechaNacimiento = formatter.parse("1998-03-11");
        this.role = role;

    }

    public Long getIdUser() {
        return idUser;
    }

    public void setidUser(Long idUser) {
        this.idUser = idUser;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getNombreUser() {
        return nombreUser;
    }

    public void setNombreUser(String nombreUser) {
        this.nombreUser = nombreUser;
    }

    public String getApellidoUser() {
        return apellidoUser;
    }

    public void setApellidoUser(String apellidoUser) {
        this.apellidoUser = apellidoUser;
    }

    public String getCorreoUser() {
        return correoUser;
    }

    public void setCorreoUser(String correoUser) {
        this.correoUser = correoUser;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}