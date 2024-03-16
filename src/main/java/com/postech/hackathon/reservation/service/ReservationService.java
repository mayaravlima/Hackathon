package com.postech.hackathon.reservation.service;

import com.postech.hackathon.client.repository.ClientRepository;
import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.entity.Room;
import com.postech.hackathon.locality.model.response.RoomResponse;
import com.postech.hackathon.locality.repository.RoomRepository;
import com.postech.hackathon.optionalService.entity.OfferedService;
import com.postech.hackathon.optionalService.entity.OptionalItem;
import com.postech.hackathon.optionalService.repository.OfferedServiceRepository;
import com.postech.hackathon.optionalService.repository.OptionalItemRepository;
import com.postech.hackathon.reservation.entity.Reservation;
import com.postech.hackathon.reservation.model.ReservationRequest;
import com.postech.hackathon.reservation.model.ReservationResponse;
import com.postech.hackathon.reservation.model.SearchAvailableRoomRequest;
import com.postech.hackathon.reservation.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final OfferedServiceRepository offeredServiceRepository;
    private final OptionalItemRepository optionalItemRepository;
    private final ClientRepository clientRepository;

    private final EmailService emailService;

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        if (isValidDates(reservationRequest.startDate(), reservationRequest.endDate())) {
            throw new DomainException("Invalid dates", HttpStatus.BAD_REQUEST.value());
        }

        var client = clientRepository.findById(reservationRequest.clientId()).orElseThrow(
                () -> new DomainException("Client not found with id: " + reservationRequest.clientId(),
                        HttpStatus.NOT_FOUND.value())
        );

        var rooms = reservationRequest.rooms().stream()
                .map(roomId -> roomRepository.findById(roomId).orElseThrow(
                        () -> new DomainException("Room not found with id: " + roomId, HttpStatus.NOT_FOUND.value())
                ))
                .toList();

        var services = reservationRequest.services().stream()
                .map(serviceId -> offeredServiceRepository.findById(serviceId).orElseThrow(
                        () -> new DomainException("Service not found with id: " + serviceId, HttpStatus.NOT_FOUND.value())
                ))
                .toList();

        var optionalItems = reservationRequest.optionals().stream()
                .map(optionalItemId -> optionalItemRepository.findById(optionalItemId).orElseThrow(
                        () -> new DomainException("Optional item not found with id: " + optionalItemId, HttpStatus.NOT_FOUND.value())
                ))
                .toList();

        var reservation = Reservation.builder()
                .client(client)
                .rooms(rooms)
                .services(services)
                .optionalItems(optionalItems)
                .startDate(reservationRequest.startDate())
                .endDate(reservationRequest.endDate())
                .numGuests(reservationRequest.numGuests())
                .build();

        var isAvailable = checkRoomAvailability(reservation);

        if (!isAvailable) {
            throw new DomainException("Rooms not available for the given dates", HttpStatus.BAD_REQUEST.value());
        }

        if (!isGuestCapacityValid(rooms, reservationRequest.numGuests())) {
            throw new DomainException("Number of guests exceeds the rooms capacity", HttpStatus.BAD_REQUEST.value());
        }

        var totalPrice = calculateTotalPrice(reservation);
        reservation.setTotalPrice(totalPrice);
        reservation.setActive(false);


        return ReservationResponse.toEntity(reservationRepository.save(reservation));
    }

    public ReservationResponse confirmReservation(Long id) {
        var reservation = reservationRepository.findById(id).orElseThrow(
                () -> new DomainException("Reservation not found with id: " + id, HttpStatus.NOT_FOUND.value())
        );

        reservation.setActive(true);

        var email = reservation.getClient().getEmail();

        var updateReservation = ReservationResponse.toEntity(reservationRepository.save(reservation));

        emailService.send(email, updateReservation);

        return updateReservation;
    }


    public ReservationResponse getReservation(Long id) {
        return ReservationResponse.toEntity(
                reservationRepository.findById(id).orElseThrow(
                        () -> new DomainException("Reservation not found with id: " + id, HttpStatus.NOT_FOUND.value())
                )
        );
    }

    public Page<ReservationResponse> getAllReservations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reservation> reservations = reservationRepository.findAll(pageable);
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::toEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(reservationResponses, pageable, reservations.getTotalElements());
    }

    public List<RoomResponse> getAvailableRooms(SearchAvailableRoomRequest request) {
        clientRepository.findById(request.clientId()).orElseThrow(
                () -> new DomainException("Client not found with id: " + request.clientId(), HttpStatus.NOT_FOUND.value())
        );
        if (isValidDates(request.startDate(), request.endDate())) {
            throw new DomainException("Invalid dates", HttpStatus.BAD_REQUEST.value());
        }

        var rooms = roomRepository.findByCapacityGreaterThanEqual(request.numGuests());

        return rooms.stream()
                .filter(room -> checkRoomAvailability(room, request.startDate(), request.endDate()))
                .filter(Room::isActive)
                .map(RoomResponse::fromEntity)
                .toList();
    }

    public void deleteReservation(Long id) {
        var reservation = reservationRepository.findById(id).orElseThrow(
                () -> new DomainException("Reservation not found with id: " + id, HttpStatus.NOT_FOUND.value())
        );

        reservationRepository.delete(reservation);
    }


    private boolean checkRoomAvailability(Reservation newReservation) {
        for (Room room : newReservation.getRooms()) {
            if (!room.isActive()) {
                throw new DomainException("Room not available: " + room.getId(), HttpStatus.BAD_REQUEST.value());
            }

            int availableRooms = room.getQuantity();

            for (Reservation existingReservation : room.getReservations()) {
                if (isOverlap(existingReservation.getStartDate(), existingReservation.getEndDate(),
                        newReservation.getStartDate(), newReservation.getEndDate())) {
                    availableRooms--;
                    if (availableRooms == 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkRoomAvailability(Room room, LocalDate startDate, LocalDate endDate) {

        int availableRooms = room.getQuantity();

        for (Reservation existingReservation : room.getReservations()) {
            if (isOverlap(existingReservation.getStartDate(), existingReservation.getEndDate(),
                    startDate, endDate)) {
                availableRooms--;
                if (availableRooms == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValidDates(LocalDate startDate, LocalDate endDate) {
        return !endDate.isAfter(startDate);
    }

    private boolean isOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }

    private boolean isGuestCapacityValid(List<Room> rooms, int totalGuests) {
        int totalCapacity = rooms.stream()
                .mapToInt(Room::getCapacity)
                .sum();

        return totalGuests <= totalCapacity;
    }

    private double calculateTotalPrice(Reservation reservation) {
        double totalPrice = 0.0;

        long numberOfDays = ChronoUnit.DAYS.between(reservation.getStartDate(), reservation.getEndDate());

        for (Room room : reservation.getRooms()) {
            double roomPrice = room.getPrice() * numberOfDays;
            totalPrice += roomPrice;
        }

        for (OptionalItem optional : reservation.getOptionalItems()) {
            totalPrice += optional.getPrice();
        }

        for (OfferedService service : reservation.getServices()) {
            totalPrice += service.getPrice();
        }

        return totalPrice;
    }


}
