package com.postech.hackathon.locality.service;

import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.entity.*;
import com.postech.hackathon.locality.model.request.*;
import com.postech.hackathon.locality.model.response.*;
import com.postech.hackathon.locality.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocalityService {

    private LocalityRepository localityRepository;
    private BuildingRepository buildingRepository;
    private RoomRepository roomRepository;
    private FurnitureRepository furnitureRepository;
    private BedRepository bedRepository;
    private BathroomRepository bathroomRepository;
    private FacilityRepository facilityRepository;
    private AmenityRepository amenityRepository;

    @Transactional
    public LocalityResponse createLocality(LocalityRequest localityRequest) {
        Locality locality = createLocalityEntity(localityRequest);
        Locality savedLocality = localityRepository.save(locality);

        List<AmenityResponse> amenities = localityRequest.amenities().stream()
                .map(amenityRequest -> createAndSaveAmenity(amenityRequest, savedLocality))
                .collect(Collectors.toList());

        List<BuildingResponse> buildings = localityRequest.buildings().stream()
                .map(buildingRequest -> createAndSaveBuilding(buildingRequest, savedLocality))
                .collect(Collectors.toList());

        return new LocalityResponse(
                savedLocality.getId(),
                savedLocality.getName(),
                AddressResponse.fromEntity(savedLocality.getAddress()),
                amenities,
                buildings);
    }

    @Transactional(readOnly = true)
    public List<LocalityResponse> getAllLocalities() {
        return localityRepository.findAll().stream()
                .map(LocalityResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LocalityResponse getLocalityById(Long id) {
        return localityRepository.findById(id)
                .map(LocalityResponse::fromEntity)
                .orElseThrow();
    }

    public Page<BuildingResponse> getBuildingsByLocalityIdPaginated(Long localityId, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return buildingRepository.findByLocalityId(localityId, pageable).map(BuildingResponse::fromEntity);
    }

    public void deleteLocality(Long id) {
        if (!localityRepository.existsById(id)) {
            throw new DomainException("Locality not found", HttpStatus.NOT_FOUND.value());
        }
        localityRepository.deleteById(id);
    }

    public LocalityResponse updateLocality(Long id, LocalityRequest request) {
        return localityRepository.findById(id)
                .map(localityEntity -> {
                    localityEntity.setName(request.name());
                    localityEntity.setAddress(getAddress(request));
                    return LocalityResponse.fromEntity(localityRepository.save(localityEntity));
                })
                .orElseThrow(() -> new DomainException("Locality not found", HttpStatus.NOT_FOUND.value()));
    }

    private Locality createLocalityEntity(LocalityRequest localityRequest) {
        Address address = getAddress(localityRequest);
        return Locality.builder()
                .name(localityRequest.name())
                .address(address)
                .build();
    }

    private AmenityResponse createAndSaveAmenity(AmenityRequest amenityRequest, Locality savedLocality) {
        Amenity amenity = Amenity.builder()
                .description(amenityRequest.description())
                .locality(savedLocality)
                .quantity(amenityRequest.quantity())
                .build();
        return AmenityResponse.fromEntity(amenityRepository.save(amenity));
    }

    private BuildingResponse createAndSaveBuilding(BuildingRequest buildingRequest, Locality savedLocality) {
        Building building = Building.builder()
                .name(buildingRequest.name())
                .locality(savedLocality)
                .build();
        Building savedBuilding = buildingRepository.save(building);

        List<RoomResponse> rooms = buildingRequest.rooms().stream()
                .map(roomRequest -> createAndSaveRoom(roomRequest, savedBuilding))
                .collect(Collectors.toList());

        return new BuildingResponse(
                savedBuilding.getId(),
                savedBuilding.getName(),
                rooms
        );
    }

    private RoomResponse createAndSaveRoom(RoomRequest roomRequest, Building savedBuilding) {
        Bathroom bathroom = createAndSaveBathroom(roomRequest.bathroom());

        List<Facility> facilities = roomRequest.bathroom().facilities().stream()
                .map(facilityRequest -> createAndSaveFacility(facilityRequest, bathroom))
                .collect(Collectors.toList());

        Room room = Room.builder()
                .type(roomRequest.type())
                .capacity(roomRequest.capacity())
                .price(roomRequest.price())
                .quantity(roomRequest.quantity())
                .building(savedBuilding)
                .bathroom(bathroom)
                .active(roomRequest.active())
                .build();
        Room savedRoom = roomRepository.save(room);

        savedRoom.getBathroom().setFacilities(facilities);

        List<FurnitureResponse> furnitures = roomRequest.furnitures().stream()
                .map(furnitureRequest -> createAndSaveFurniture(furnitureRequest, savedRoom))
                .collect(Collectors.toList());

        List<BedResponse> beds = roomRequest.beds().stream()
                .map(bedRequest -> createAndSaveBed(bedRequest, savedRoom))
                .collect(Collectors.toList());

        return new RoomResponse(
                savedRoom.getId(),
                savedRoom.getType(),
                savedRoom.getCapacity(),
                furnitures,
                beds,
                BathroomResponse.fromEntity(savedRoom.getBathroom()),
                savedRoom.getPrice(),
                savedRoom.getQuantity(),
                savedRoom.isActive()
        );

    }

    private FurnitureResponse createAndSaveFurniture(FurnitureRequest furnitureRequest, Room savedRoom) {
        Furniture furniture = Furniture.builder()
                .name(furnitureRequest.name())
                .quantity(furnitureRequest.quantity())
                .room(savedRoom)
                .build();
        return FurnitureResponse.fromEntity(furnitureRepository.save(furniture));
    }

    private BedResponse createAndSaveBed(BedRequest bedRequest, Room savedRoom) {
        Bed bed = Bed.builder()
                .name(bedRequest.name())
                .quantity(bedRequest.quantity())
                .room(savedRoom)
                .build();
        return BedResponse.fromEntity(bedRepository.save(bed));
    }

    private Bathroom createAndSaveBathroom(BathroomRequest bathroomRequest) {
        Bathroom bathroom = Bathroom.builder()
                .type(bathroomRequest.type())
                .build();
        return bathroomRepository.save(bathroom);
    }

    private Facility createAndSaveFacility(FacilityRequest facilityRequest, Bathroom savedBathroom) {
        Facility facility = Facility.builder()
                .name(facilityRequest.name())
                .bathroom(savedBathroom)
                .build();
        return facilityRepository.save(facility);
    }

    private Address getAddress(LocalityRequest localityRequest) {
        return Address.builder()
                .street(localityRequest.address().street())
                .number(localityRequest.address().number())
                .complement(localityRequest.address().complement())
                .neighborhood(localityRequest.address().neighborhood())
                .city(localityRequest.address().city())
                .state(localityRequest.address().state())
                .country(localityRequest.address().country())
                .zipcode(localityRequest.address().zipcode())
                .build();
    }
}
