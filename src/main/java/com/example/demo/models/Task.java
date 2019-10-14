package com.example.demo.models;

import com.example.demo.models.Requirement;
import com.example.demo.models.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tasks")

public class Task {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "`type`", unique = false)
    private String type;

    @NotNull
    @Column(nullable = false, name = "description", unique = false)
    private String description;

    @NotNull
    @Column(nullable = false, name = "`capacity`", unique = false)
    private Integer capacity;

    @NotNull
    @Column(nullable = false, name = "`status`", unique = false)
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "emergency_id")
    private Emergency emergency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructor Vacío.
    public Task() { }

    // Constructor.
    public Task( String type, String description, Integer capacity, Integer status, Emergency emergency, User user) {
        this.type = type;
        this.description = description;
        this.capacity = capacity;
        this.status = status;
        this.emergency = emergency;
        this.user = user;
    }

    // Getter y Setter.
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getCapacity() { return capacity; }

    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public Emergency getEmergency() { return emergency; }

    public void setEmergency(Emergency emergency) { this.emergency = emergency; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
