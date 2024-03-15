package com.postech.hackathon.reservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.postech.hackathon.client.model.ClientResponse;
import com.postech.hackathon.locality.model.response.RoomResponse;
import com.postech.hackathon.optionalService.model.OfferedServiceResponse;
import com.postech.hackathon.optionalService.model.OptionalItemResponse;
import com.postech.hackathon.reservation.entity.Reservation;

import java.time.LocalDate;
import java.util.List;

public record ReservationResponse (
        Long id,
        ClientResponse client,
        List<OfferedServiceResponse> service,
        List<OptionalItemResponse> optionals,
        List<RoomResponse> rooms,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate startDate,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate endDate,
        int numGuests,

        double totalPrice
        ) {

        public static ReservationResponse toEntity(Reservation reservation) {
                reservation.getServices().size();
                reservation.getOptionalItems().size();
                reservation.getRooms().size();
                return new ReservationResponse(
                        reservation.getId(),
                        ClientResponse.fromEntity(reservation.getClient()),
                        reservation.getServices().stream().map(OfferedServiceResponse::fromEntity).toList(),
                        reservation.getOptionalItems().stream().map(OptionalItemResponse::fromEntity).toList(),
                        reservation.getRooms().stream().map(RoomResponse::fromEntity).toList(),
                        reservation.getStartDate(),
                        reservation.getEndDate(),
                        reservation.getNumGuests(), reservation.getTotalPrice());

        }

}
