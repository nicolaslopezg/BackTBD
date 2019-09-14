package com.example.demo.Models;

import org.springframework.lang.NonNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dimensions")

public class Dimension {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String type) {
        this.name = name;
    }
}