package com.postech.hackathon.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.postech.hackathon.client.entity.Client;
import com.postech.hackathon.client.utils.Country;
import com.postech.hackathon.locality.model.response.AddressResponse;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ClientResponse(
        long id,
        String name,
        @JsonProperty("origin_country")
        Country originCountry,
        String cpf,
        @JsonProperty("passport_number")
        String passportNumber,
        @JsonFormat(pattern = "dd/MM/yyyy")
        @JsonProperty("date_of_birth")
        LocalDate dateOfBirth,
        AddressResponse address,
        String email,
        @JsonProperty("phone_number")
        String phoneNumber
) {

    public static ClientResponse fromEntity(Client client) {
        return new ClientResponse (
                client.getId(),
                client.getName(),
                client.getOriginCountry(),
                client.getCpf(),
                client.getPassportNumber(),
                client.getDateOfBirth(),
                AddressResponse.fromEntity(client.getAddress()),
                client.getEmail(),
                client.getPhoneNumber()
        );
    }
}
