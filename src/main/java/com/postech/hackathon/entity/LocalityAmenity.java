package com.postech.hackathon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "locality_amenity")
public class LocalityAmenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_locality")
    private Locality locality;

    @ManyToOne
    @JoinColumn(name = "id_amenity")
    private Amenity amenity;
}
