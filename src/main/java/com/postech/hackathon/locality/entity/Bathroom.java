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
@Table(name = "bathroom")
public class Bathroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "bathroom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Facility> facilities;

}
