package com.postech.hackathon.locality.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "locality_building")
public class LocalityBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_locality")
    private Locality locality;

    @ManyToOne
    @JoinColumn(name = "id_building")
    private Building building;
}
