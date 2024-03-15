package com.postech.hackathon.locality.service;

import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.model.request.BuildingRequest;
import com.postech.hackathon.locality.model.response.BuildingResponse;
import com.postech.hackathon.locality.repository.BuildingRepository;
import com.postech.hackathon.locality.repository.LocalityRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BuildingService {

    private BuildingRepository buildingRepository;
    private LocalityRepository localityRepository;

    public BuildingResponse createBuilding(BuildingRequest buildingRequest, Long idLocality) {

        var locality = localityRepository.findById(idLocality)
                .orElseThrow(() -> new DomainException("Locality not found", HttpStatus.NOT_FOUND.value()));

        var newBuilding = buildingRequest.toEntity();
        newBuilding.setLocality(locality);

        return BuildingResponse.fromEntity(buildingRepository.save(newBuilding));
    }

    public BuildingResponse getBuilding(Long id) {
        return BuildingResponse.fromEntity(
                buildingRepository.findById(id).orElseThrow(() -> new DomainException("Building not found", HttpStatus.NOT_FOUND.value()))
        );
    }

    public List<BuildingResponse> getAllBuildings() {
        return buildingRepository.findAll().stream()
                .map(BuildingResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteBuilding(Long id) {
        if (!buildingRepository.existsById(id)) {
            throw new DomainException("Building not found", HttpStatus.NOT_FOUND.value());
        }
        buildingRepository.deleteById(id);
    }

    public BuildingResponse updateBuilding(Long id, BuildingRequest request) {
        var building = buildingRepository.findById(id)
                .orElseThrow(() -> new DomainException("Building not found", HttpStatus.NOT_FOUND.value()));

        building.setName(request.name());

        return BuildingResponse.fromEntity(buildingRepository.save(building));
    }
}
