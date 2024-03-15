package com.postech.hackathon.locality.controller;

import com.postech.hackathon.locality.model.request.RoomRequest;
import com.postech.hackathon.locality.model.response.RoomResponse;
import com.postech.hackathon.locality.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/rooms")
@AllArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/{idBuilding}")
    public ResponseEntity<RoomResponse> createRoom(@RequestBody RoomRequest roomRequest, @PathVariable Long idBuilding) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(roomRequest, idBuilding));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {
        return ResponseEntity.status(200).body(roomService.getRoom(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllRooms() {
        return ResponseEntity.status(200).body(roomService.getAllRooms());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long id, @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.updateRoom(id, request));
    }




}
