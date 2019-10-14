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

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, name = "`rut`", unique = true)
    private int rut;

    @Column(nullable = false, name = "`name`")
    private String name;

    @Column(nullable = false, name = "`lastname`")
    private String lastname;

    @Column(nullable = false, name = "`mail`")
    private String mail;

    @Column(nullable = false, name = "`birthDate`")
    private Date birthDate;

    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    // Constructor Vacío.
    public User() { }

    //Constructor.
    public User(int rut, String name, String lastname, String mail,Role role) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.rut = rut;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.birthDate = formatter.parse("1998-03-11");
        this.role = role;

    }

    // Getter y Setter.
    public Long getId() {
        return id;
    }

    public void setId(Long idUser) {
        this.id = idUser;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String correoUser) {
        this.mail = mail;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}