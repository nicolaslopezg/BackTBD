package com.example.demo.models;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="equipments")
public class Equipment implements Serializable {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false, name = "`code`", unique = true)
    private Long code;

    @Column(nullable = false, name = "`type`")
    private int type;

    @Column(nullable = false, name = "`name`")
    private String name;

    @Column(nullable = false, name = "`certification`")
    private String certification;

    @Column(nullable = false, name = "`detail`")
    private String detail;


    //@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "idCliente")
    //@JsonIgnore
    //private List<Reserva> reservas;

    // Constructor Vacío.
    public Equipment() { }

    // Constructor.
    public Equipment( Long code, int type, String name,String certification, String detail) {
        this.code = code;
        this.type = type;
        this.name = name;
        this.certification = certification;
        this.detail = detail;
    }

    // Getter y Setter.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) { this.certification = certification; }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}