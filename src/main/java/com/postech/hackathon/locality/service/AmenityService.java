package com.postech.hackathon.locality.service;

import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.model.request.AmenityRequest;
import com.postech.hackathon.locality.model.response.AmenityResponse;
import com.postech.hackathon.locality.repository.AmenityRepository;
import com.postech.hackathon.locality.repository.LocalityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AmenityService {

    private AmenityRepository amenityRepository;
    private LocalityRepository localityRepository;

    public AmenityResponse createAmenity(AmenityRequest amenityRequest, Long idLocality) {

        var locality = localityRepository.findById(idLocality)
                .orElseThrow(() -> new DomainException("Locality not found", HttpStatus.NOT_FOUND.value()));

        var newAmenity = amenityRequest.toEntity();
        newAmenity.setLocality(locality);

        return AmenityResponse.fromEntity(amenityRepository.save(newAmenity));
    }

    public AmenityResponse getAmenity(Long id) {
        return AmenityResponse.fromEntity(
                amenityRepository.findById(id).orElseThrow(() -> new DomainException("Amenity not found", HttpStatus.NOT_FOUND.value()))
        );
    }

    public List<AmenityResponse> getAllAmenities() {
        return amenityRepository.findAll().stream()
                .map(AmenityResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteAmenity(Long id) {
        if (!amenityRepository.existsById(id)) {
            throw new DomainException("Amenity not found", HttpStatus.NOT_FOUND.value());
        }
        amenityRepository.deleteById(id);
    }

    public AmenityResponse updateAmenity(Long id, AmenityRequest request) {
        var amenity = amenityRepository.findById(id)
                .orElseThrow(() -> new DomainException("Amenity not found", HttpStatus.NOT_FOUND.value()));

        amenity.setDescription(request.description());
        amenity.setQuantity(request.quantity());

        return AmenityResponse.fromEntity(amenityRepository.save(amenity));
    }



}
