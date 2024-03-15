package com.postech.hackathon.reservation.entity;

import com.postech.hackathon.client.entity.Client;
import com.postech.hackathon.locality.entity.Room;
import com.postech.hackathon.optionalService.entity.OfferedService;
import com.postech.hackathon.optionalService.entity.OptionalItem;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reservation_service",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "services_id"))
    private List<OfferedService> services;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reservation_optional",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "optional_id"))
    private List<OptionalItem> optionalItems;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reservation_room",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms;

    private LocalDate startDate;
    private LocalDate endDate;
    private int numGuests;
    private double totalPrice;
    private boolean active;
}
