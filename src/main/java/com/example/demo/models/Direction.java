package com.example.demo.models;

import javax.validation.constraints.NotNull;
import com.example.demo.models.District;
import javax.persistence.*;

@Entity
@Table(name = "directions")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, name = "street", unique = false)
    private String street;

    @NotNull
    @Column(nullable = false, name = "number", unique = false)
    private Integer number;

    @ManyToOne
    @JoinColumn
    private Emergency emergency;

    @ManyToOne
    @JoinColumn
    private District district;

    @ManyToOne
    @JoinColumn (name = "voluntary_id")
    private Voluntary voluntary;

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
