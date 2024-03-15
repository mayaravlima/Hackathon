package com.postech.hackathon.reservation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SearchAvailableRoomRequest(
        @NotNull(message = "Client id is required")
        @JsonProperty("client_id")
        Long clientId,

        @NotNull(message = "Number of guests is required")
        @JsonProperty("num_guests")
        int numGuests,

        @NotNull(message = "Start date is required")
        @JsonProperty("start_date")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        @JsonProperty("end_date")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate endDate
) {
}
