package com.example.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "emergencies")
public class Emergency {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "type", unique = false)
    private String type;

    @NotNull
    @Column(nullable = false, name = "description", unique = false)
    private String description;

    @NotNull
    @Column(nullable = false, name = "capacity", unique = false)
    private Integer capacity;

    @NotNull
    @Column(nullable = false, name = "status", unique = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "`latitude`")
    private String latitude;

    @Column(name = "`longitude`")
    private String longitude;

    @Column(columnDefinition = "geometry(Point, 4326)", name = "`location`", nullable = true)
    @JsonIgnore
    private Point location;

    // Constructor Vacío.
    public Emergency() { }

    //Mew Constructor
    public Emergency( String type, String description, Integer capacity, Integer status, User user, String latitude, String longitude) {
        this.type = type;
        this.description = description;
        this.capacity = capacity;
        this.status = status;
        this.user = user;

        this.latitude = latitude;
        this.longitude = longitude;

        GeometryFactory geometryFactory = new GeometryFactory();

        Coordinate coordinate = new Coordinate();
        coordinate.x = Double.parseDouble(latitude);;
        coordinate.y = Double.parseDouble(longitude);;

        this.location = geometryFactory.createPoint(coordinate);
        this.location.setSRID(4326);
    }

    // Constructor.
    public Emergency( String type, String description, Integer capacity, Integer status, User user) {
        this.type = type;
        this.description = description;
        this.capacity = capacity;
        this.status = status;
        this.user = user;
    }


    // Getter y Setter.
    public Long getIdEmergency() { return id; }

    public void setIdEmergency(Long idEmergency) {
        this.id = idEmergency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
