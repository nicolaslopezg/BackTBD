package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.postgis.Geometry;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="volunteers")
public class Voluntary implements Serializable {

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

    @Column(nullable = false, name = "`asignated`")
    private Boolean asignated;

    @Column(name = "`gender`")
    private String gender;

    @Column(name = "`latitude`")
    private String latitude;

    @Column(name = "`longitude`")
    private String longitude;

    @Column(columnDefinition = "geometry(Point, 4326)", name = "`location`", nullable = true)
    @JsonIgnore
    private Point location;


    // Constructor Vacío.
    public Voluntary() { }

    //Constructor para voluntarios leidos desde el csv.
    public Voluntary(String name, String lastname, String mail, String gender, int rut, String latitude, String longitude) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.rut = rut;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.birthDate = formatter.parse("1998-03-11");
        this.asignated = false;
        this.gender = gender;
        this.latitude = latitude;
        this.longitude = longitude;

        GeometryFactory geometryFactory = new GeometryFactory();

        Coordinate coordinate = new Coordinate();
        coordinate.x = Double.parseDouble(latitude);
        coordinate.y = Double.parseDouble(longitude);

        this.location = geometryFactory.createPoint(coordinate);
        this.location.setSRID(4326);

    }

    // Constructor.
    public Voluntary( int rut, String name,String lastname, String mail ,Date birthDate, Boolean asignated) {
        this.rut = rut;
        this.name = name;
        this.lastname = lastname;
        this.mail = mail;
        this.birthDate = birthDate;
        this.asignated = asignated;
    }

    // Getter y Setter.
    public Long getId() {
        return id;
    }

    public void setId(Long idUsuario) {
        this.id = id;
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

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getAsignated() {
        return asignated;
    }

    public void setAsignated(Boolean asignated) {
        this.asignated = asignated;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public Point getLocation() { return location;}

    public void setLocation(Double x, Double y) {
        Double i,j;
        i = Double.parseDouble(latitude);
        j = Double.parseDouble(longitude);

        GeometryFactory geometryFactory = new GeometryFactory();

        Coordinate coordinate = new Coordinate();
        coordinate.x = i;
        coordinate.y = j;

        this.location = geometryFactory.createPoint(coordinate);
    }

}