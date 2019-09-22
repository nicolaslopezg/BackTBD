package com.example.demo.models;

import javax.validation.constraints.NotNull;
import javax.persistence.*;

@Entity
@Table(name = "emergencies")
public class Emergency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmergency", updatable = false, nullable = false)
    private Long idEmergency;

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

    public Emergency() {

    }

    public Emergency( String type, String description, Integer capacity, Integer status) {
        this.type = type;
        this.description = description;
        this.capacity = capacity;
        this.status = status;
    }

    public Long getIdEmergency() {
        return idEmergency;
    }

    public void setIdEmergency(Long idEmergency) {
        this.idEmergency = idEmergency;
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
}
