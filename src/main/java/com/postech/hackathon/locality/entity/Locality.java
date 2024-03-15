package com.postech.hackathon.locality.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locality")
public class Locality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "locality", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Building> buildings;

    @OneToMany(mappedBy = "locality", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Amenity> amenities;

}
