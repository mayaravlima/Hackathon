package com.postech.hackathon.locality.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.postech.hackathon.locality.entity.Address;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AddressResponse(
        long id,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String country,
        String zipcode
) {

    public static AddressResponse fromEntity(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipcode()
        );
    }
}
