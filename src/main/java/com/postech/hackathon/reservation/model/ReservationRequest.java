package com.postech.hackathon.reservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ReservationRequest(

        @NotNull(message = "Client ID is required")
        @JsonProperty("client_id")
        Long clientId,
        List<Long> services,
        List<Long> optionals,
        @NotNull(message = "Room ID is required")
        List<Long> rooms,
        @NotNull(message = "Start date is required")
        @FutureOrPresent(message = "Start date must be in the present or future")
        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("start_date")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("end_date")
        @FutureOrPresent(message = "Start date must be in the present or future")
        LocalDate endDate,

        @NotNull(message = "Number of guests is required")
        @JsonProperty("num_guests")
        int numGuests
) {
}
