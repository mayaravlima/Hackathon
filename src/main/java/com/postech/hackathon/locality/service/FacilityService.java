package com.postech.hackathon.locality.service;

import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.model.request.FacilityRequest;
import com.postech.hackathon.locality.model.response.FacilityResponse;
import com.postech.hackathon.locality.repository.BathroomRepository;
import com.postech.hackathon.locality.repository.FacilityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FacilityService {

    private FacilityRepository facilityRepository;
    private BathroomRepository bathroomRepository;

    public FacilityResponse createFacility(FacilityRequest facilityRequest, Long idBathroom) {
        var bathroom = bathroomRepository.findById(idBathroom)
                .orElseThrow(() -> new DomainException("Bathroom not found", HttpStatus.NOT_FOUND.value()));

        bathroom.getFacilities().add(facilityRequest.toEntity());
        var newFacility = facilityRequest.toEntity();
        return FacilityResponse.fromEntity(facilityRepository.save(newFacility));
    }

    public FacilityResponse getFacility(Long id) {
        return FacilityResponse.fromEntity(
                facilityRepository.findById(id).orElseThrow(() -> new DomainException("Facility not found", HttpStatus.NOT_FOUND.value()))
        );
    }

    public List<FacilityResponse> getAllFacilities() {
        return facilityRepository.findAll().stream()
                .map(FacilityResponse::fromEntity)
                .collect(java.util.stream.Collectors.toList());
    }

    public void deleteFacility(Long id) {
        if (!facilityRepository.existsById(id)) {
            throw new DomainException("Facility not found", HttpStatus.NOT_FOUND.value());
        }
        facilityRepository.deleteById(id);
    }

    public FacilityResponse updateFacility(Long id, FacilityRequest request) {
        var facility = facilityRepository.findById(id)
                .orElseThrow(() -> new DomainException("Facility not found", HttpStatus.NOT_FOUND.value()));

        facility.setName(request.name());
        return FacilityResponse.fromEntity(facilityRepository.save(facility));
    }
}
