package com.example.demo.models;

import com.example.demo.models.Requirement;
import com.example.demo.models.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tasks")

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "`type`", unique = true)
    private String type;

    @NotNull
    @Column(nullable = false, name = "description", unique = true)
    private String description;

    @NotNull
    @Column(nullable = false, name = "`capacity`", unique = true)
    private Integer capacity;

    @NotNull
    @Column(nullable = false, name = "`state`", unique = true)
    private Integer state;

    @ManyToOne
    @JoinColumn
    private Emergency emergency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Integer getCapacity() { return capacity; }

    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getState() { return state; }

    public void setState(Integer state) { this.state = state; }
}
