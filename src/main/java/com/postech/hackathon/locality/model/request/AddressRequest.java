package com.postech.hackathon.locality.model.request;

import com.postech.hackathon.locality.entity.Address;
import jakarta.validation.constraints.NotNull;

public record AddressRequest(

        @NotNull(message = "Street can't be empty or null")
        String street,

        @NotNull(message = "Number can't be empty or null")
        String number,
        String complement,

        @NotNull(message = "Neighborhood can't be empty or null")
        String neighborhood,

        @NotNull(message = "City can't be empty or null")
        String city,

        @NotNull(message = "State can't be empty or null")
        String state,

        @NotNull(message = "Country can't be empty or null")
        String country,

        @NotNull(message = "Zip code can't be empty or null")
        String zipcode
) {

    public Address toEntity() {
        return Address.builder()
                .street(this.street())
                .number(this.number())
                .complement(this.complement())
                .neighborhood(this.neighborhood())
                .city(this.city())
                .state(this.state())
                .country(this.country())
                .zipcode(this.zipcode())
                .build();
    }
}
