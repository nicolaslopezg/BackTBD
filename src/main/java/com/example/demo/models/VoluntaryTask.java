package com.example.demo.models;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;
import javax.persistence.*;

@Entity
@Table(name = "voluntary_tasks")

public class VoluntaryTask {

    // Columnas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "voluntary_id")
    private Voluntary voluntary;

    // Constructor Vacío.
    public VoluntaryTask(){ }

    // Constructor.
    public VoluntaryTask( int status, Task task, Voluntary voluntary) {
        this.status = status;
        this.task = task;
        this.voluntary = voluntary;
    }

    // Getter y Setter.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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