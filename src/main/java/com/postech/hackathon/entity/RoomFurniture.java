package com.postech.hackathon.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "room_furniture")
public class RoomFurniture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_furniture")
    private Furniture furniture;
}
