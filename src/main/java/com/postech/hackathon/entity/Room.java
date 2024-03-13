package com.postech.hackathon.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "room")
    private List<RoomBed> quantityBed;

    @OneToMany(mappedBy = "room")
    private List<RoomFurniture> roomFurniture;

    @ManyToOne
    @JoinColumn(name = "id_bathroom")
    private Bathroom bathroom;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_building")
    private Building building;

    @ManyToOne
    @JoinColumn(name = "id_locality")
    private Locality locality;
}
