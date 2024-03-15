package com.postech.hackathon.locality.service;

import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.model.request.BedRequest;
import com.postech.hackathon.locality.model.response.BedResponse;
import com.postech.hackathon.locality.repository.BedRepository;
import com.postech.hackathon.locality.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BedService {

    private BedRepository bedRepository;
    private RoomRepository roomRepository;


    public BedResponse createBed(BedRequest bedRequest, Long idRoom) {
        var room = roomRepository.findById(idRoom)
                .orElseThrow(() -> new DomainException("Room not found", HttpStatus.NOT_FOUND.value()));

        room.getBeds().add(bedRequest.toEntity());
        var newBed = bedRequest.toEntity();
        return BedResponse.fromEntity(bedRepository.save(newBed));
    }

    public BedResponse getBed(Long id) {
        return BedResponse.fromEntity(
                bedRepository.findById(id).orElseThrow(() -> new DomainException("Bed not found", HttpStatus.NOT_FOUND.value()))
        );
    }

    public List<BedResponse> getAllBeds() {
        return bedRepository.findAll().stream()
                .map(BedResponse::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }

    public void deleteBed(Long id) {
        if (!bedRepository.existsById(id)) {
            throw new DomainException("Bed not found", HttpStatus.NOT_FOUND.value());
        }
        bedRepository.deleteById(id);
    }

    public BedResponse updateBed(Long id, BedRequest request) {
        var bed = bedRepository.findById(id)
                .orElseThrow(() -> new DomainException("Bed not found", HttpStatus.NOT_FOUND.value()));

        bed.setName(request.name());
        bed.setQuantity(request.quantity());
        return BedResponse.fromEntity(bedRepository.save(bed));
    }


}
