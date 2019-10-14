package com.example.demo.models;

import javax.validation.constraints.NotNull;
import com.example.demo.models.District;
import javax.persistence.*;

@Entity
@Table(name = "directions")
public class Direction {

    // Columnas.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "street", unique = false)
    private String street;

    @NotNull
    @Column(nullable = false, name = "number", unique = false)
    private Integer number;

    @ManyToOne
    @JoinColumn (name = "emergency_id")
    private Emergency emergency;

    @ManyToOne
    @JoinColumn (name = "district_id")
    private District district;

    @ManyToOne
    @JoinColumn (name = "voluntary_id")
    private Voluntary voluntary;

    // Constructor Vacío.
    public Direction() { }

    // Constructor.
    public Direction(String street, int number, Emergency emergency, District district, Voluntary voluntary) {
        this.street = street;
        this.number = number;
        this.emergency = emergency;
        this.district = district;
        this.voluntary = voluntary;
    }

    // Getter y Setter.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
