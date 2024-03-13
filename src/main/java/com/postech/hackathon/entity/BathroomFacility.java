package com.postech.hackathon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bathroom_facility")
public class BathroomFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_bathroom")
    private Bathroom bathroom;

    @ManyToOne
    @JoinColumn(name = "id_facility")
    private Facility facility;
}
