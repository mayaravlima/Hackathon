package com.postech.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.postech.hackathon.entity.Client;
import com.postech.hackathon.utils.Country;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ClientRequest(

        @NotNull(message = "Name can't be empty or null")
        @Pattern(regexp = "[a-zA-Z\\s-]+", message = "Name must contain only letters, spaces, or hyphens")
        @Size(min = 5, max = 50, message = "Name must be between 5 and 50 characters")
        String name,

        @NotNull(message = "Origin country can't be empty or null")
        @JsonProperty("origin_country")
        Country originCountry,

        @CPF(message = "Invalid CPF")
        String cpf,

        @Pattern(regexp = "[a-zA-Z0-9]+", message = "Passport number must contain only letters and numbers")
        @Size(min = 5, max = 20, message = "Passport number must be between 5 and 20 characters")
        @JsonProperty("passport_number")
        String passportNumber,

        @NotNull(message = "Date of birth name can't be empty or null")
        @Past(message = "Date of birth must be in the past")
        @JsonProperty("date_of_birth")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dateOfBirth,

        @NotNull(message = "Address can't be empty or null")
        @Size(min = 5, max = 100, message = "Address must be between 5 and 100 characters")
        String address,

        @NotNull(message = "Email can't be empty or null")
        @Email(message = "Invalid email")
        String email,

        @NotNull(message = "Phone number can't be empty or null")
        @Size(min = 10, max = 15, message = "Phone number must be between 10 and 11 characters")
        @JsonProperty("phone_number")
        String phoneNumber
) {

    public Client fromDTO(ClientRequest request) {
        return Client.builder()
                .name(request.name())
                .originCountry(request.originCountry())
                .cpf(request.cpf())
                .passportNumber(request.passportNumber())
                .dateOfBirth(request.dateOfBirth())
                .address(request.address())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .build();
    }
}
