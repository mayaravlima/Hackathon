package com.postech.hackathon.reservation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postech.hackathon.locality.model.response.RoomResponse;
import com.postech.hackathon.reservation.model.ReservationRequest;
import com.postech.hackathon.reservation.model.ReservationResponse;
import com.postech.hackathon.reservation.model.SearchAvailableRoomRequest;
import com.postech.hackathon.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/checkout")
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.status(201).body(reservationService.createReservation(reservationRequest));
    }

    @PutMapping("/confirm/{id}")
    public ResponseEntity<ReservationResponse> confirmReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.confirmReservation(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> getReservation(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> getAllReservations(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(reservationService.getAllReservations(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomResponse>> getAvailableRooms(@RequestBody @Valid SearchAvailableRoomRequest searchAvailableRoomRequest) {
        return ResponseEntity.ok(reservationService.getAvailableRooms(searchAvailableRoomRequest));
    }
}
