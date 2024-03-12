package com.postech.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.postech.hackathon.entity.Client;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientResponse(
        Long id,
        String name,
        @JsonProperty("origin_country")
        String originCountry,
        String cpf,
        @JsonProperty("passport_number")
        String passportNumber,
        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("date_of_birth")
        LocalDate dateOfBirth,
        String address,
        String email,
        @JsonProperty("phone_number")
        String phoneNumber
) {

    public static ClientResponse fromEntity(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getName(),
                client.getOriginCountry(),
                client.getCpf(),
                client.getPassportNumber(),
                client.getDateOfBirth(),
                client.getAddress(),
                client.getEmail(),
                client.getPhoneNumber()
        );
    }
}
