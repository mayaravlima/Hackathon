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
@Table(name = "building")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Room> rooms;

    @ManyToOne
    @JoinColumn(name = "id_locality")
    private Locality locality;



}
