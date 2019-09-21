package com.example.demo.Models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;
import javax.persistence.*;

@Entity
@Table(name = "voluntary_tasks")

public class VoluntaryTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn
    private VoluntaryTask dimension;
    /*
    @ManyToOne
    @JoinColumn
    private Voluntary voluntary;*/

    // Faltan las llaves foraneas.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}