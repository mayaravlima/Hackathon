package com.postech.hackathon.optionalService.service;


import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.optionalService.model.OfferedServiceRequest;
import com.postech.hackathon.optionalService.model.OfferedServiceResponse;
import com.postech.hackathon.optionalService.repository.OfferedServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferedServiceService {

    private OfferedServiceRepository offeredServiceRepository;

    public OfferedServiceResponse saveOfferedService(OfferedServiceRequest request) {
        var service = offeredServiceRepository.save(request.toEntity());
        return OfferedServiceResponse.fromEntity(service);
    }

    public OfferedServiceResponse getOfferedService(Long id) {
        return offeredServiceRepository.findById(id)
                .map(OfferedServiceResponse::fromEntity)
                .orElseThrow(() -> new DomainException("Service not found", HttpStatus.NOT_FOUND.value()));
    }


    public void deleteOfferedService(Long id) {
        if (!offeredServiceRepository.existsById(id)) {
            throw new DomainException("Service not found", HttpStatus.NOT_FOUND.value());
        }
        offeredServiceRepository.deleteById(id);
    }

    public OfferedServiceResponse updateOfferedService(Long id, OfferedServiceRequest request) {
        var service = offeredServiceRepository.findById(id)
                .orElseThrow(() -> new DomainException("Service not found", HttpStatus.NOT_FOUND.value()));

        service.setPrice(request.price());
        service.setName(request.name());

        return OfferedServiceResponse.fromEntity(offeredServiceRepository.save(service));
    }

    public List<OfferedServiceResponse> getOfferedServices() {
        return offeredServiceRepository.findAll()
                .stream()
                .map(OfferedServiceResponse::fromEntity)
                .collect(Collectors.toList());
    }


}
