package com.example.demo.models;

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
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "voluntary_id")
    private Voluntary voluntary;

    // Faltan las llaves foraneas.

    public VoluntaryTask() {

    }

    public VoluntaryTask( Task task, Voluntary voluntary) {
        this.task = task;
        this.voluntary = voluntary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Voluntary getVoluntary() {
        return voluntary;
    }

    public void setVoluntary(Voluntary task) {
        this.voluntary = voluntary;
    }
}