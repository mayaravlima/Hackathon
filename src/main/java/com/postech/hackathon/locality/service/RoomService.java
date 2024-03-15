package com.postech.hackathon.locality.service;

import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.model.request.RoomRequest;
import com.postech.hackathon.locality.model.response.RoomResponse;
import com.postech.hackathon.locality.repository.BuildingRepository;
import com.postech.hackathon.locality.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final BuildingRepository buildingRepository;

    public RoomResponse createRoom(RoomRequest roomRequest, Long idBuilding) {
        var building = buildingRepository.findById(idBuilding)
                .orElseThrow(() -> new DomainException("Building not found", HttpStatus.NOT_FOUND.value()));

        var newRoom = roomRequest.toEntity();
        newRoom.setBuilding(building);
        return RoomResponse.fromEntity(roomRepository.save(newRoom));
    }

    public RoomResponse getRoom(Long id) {
        return RoomResponse.fromEntity(
                roomRepository.findById(id).orElseThrow(() -> new DomainException("Room not found", HttpStatus.NOT_FOUND.value()))
        );
    }

    public List<RoomResponse> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomResponse::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new DomainException("Room not found", HttpStatus.NOT_FOUND.value());
        }
        roomRepository.deleteById(id);
    }

    public RoomResponse updateRoom(Long id, RoomRequest request) {
        var room = roomRepository.findById(id)
                .orElseThrow(() -> new DomainException("Room not found", HttpStatus.NOT_FOUND.value()));

        room.setCapacity(request.capacity());
        room.setPrice(request.price());
        room.setQuantity(request.quantity());
        room.setActive(request.active());
        return RoomResponse.fromEntity(roomRepository.save(room));
    }

}
