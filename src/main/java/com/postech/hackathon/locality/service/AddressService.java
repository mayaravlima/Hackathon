package com.postech.hackathon.locality.service;

import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.model.request.AddressRequest;
import com.postech.hackathon.locality.model.response.AddressResponse;
import com.postech.hackathon.locality.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;

    public AddressResponse getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(AddressResponse::fromEntity)
                .orElseThrow(() -> new DomainException("Address not found", HttpStatus.NOT_FOUND.value()));
    }

    public AddressResponse updateAddress(AddressRequest addressRequest, Long idLocality) {
        return addressRepository.findById(idLocality)
                .map(addressEntity -> {
                    addressEntity.setCity(addressRequest.city());
                    addressEntity.setCountry(addressRequest.country());
                    addressEntity.setStreet(addressRequest.street());
                    addressEntity.setZipcode(addressRequest.zipcode());
                    return AddressResponse.fromEntity(addressRepository.save(addressEntity));
                })
                .orElseThrow(() -> new DomainException("Address not found", HttpStatus.NOT_FOUND.value()));
    }
}
