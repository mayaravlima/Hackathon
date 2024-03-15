package com.postech.hackathon.locality.service;

import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.model.request.FurnitureRequest;
import com.postech.hackathon.locality.model.response.FurnitureResponse;
import com.postech.hackathon.locality.repository.FurnitureRepository;
import com.postech.hackathon.locality.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FurnitureService {

    private FurnitureRepository furnitureRepository;
    private RoomRepository roomRepository;

    public void deleteFurniture(Long id) {
        if (!furnitureRepository.existsById(id)) {
            throw new DomainException("Furniture not found", HttpStatus.NOT_FOUND.value());
        }
        furnitureRepository.deleteById(id);
    }

    public void updateFurniture(Long id, FurnitureRequest request) {
        var furniture = furnitureRepository.findById(id)
                .orElseThrow(() -> new DomainException("Furniture not found", HttpStatus.NOT_FOUND.value()));

        furniture.setName(request.name());
        furniture.setQuantity(request.quantity());
        furnitureRepository.save(furniture);
    }

    public FurnitureResponse createFurniture(FurnitureRequest furnitureRequest, Long idRoom) {
        var room = roomRepository.findById(idRoom)
                .orElseThrow(() -> new DomainException("Room not found", HttpStatus.NOT_FOUND.value()));

        room.getFurnitures().add(furnitureRequest.toEntity());
        var newFurniture = furnitureRequest.toEntity();
        return FurnitureResponse.fromEntity(furnitureRepository.save(newFurniture));
    }

    public FurnitureResponse getFurniture(Long id) {
        return FurnitureResponse.fromEntity(
                furnitureRepository.findById(id).orElseThrow(() -> new DomainException("Furniture not found", HttpStatus.NOT_FOUND.value()))
        );
    }

    public List<FurnitureResponse> getAllFurnitures() {
        return furnitureRepository.findAll().stream()
                .map(FurnitureResponse::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }

}
