package com.postech.hackathon.locality.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "room_bed")
public class RoomBed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_bed")
    private Bed bed;

}
