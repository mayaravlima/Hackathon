package com.postech.hackathon.optionalService.service;


import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.optionalService.model.OptionalItemRequest;
import com.postech.hackathon.optionalService.model.OptionalItemResponse;
import com.postech.hackathon.optionalService.repository.OptionalItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OptionalItemService {

    private OptionalItemRepository optionalItemRepository;

    public OptionalItemResponse saveOptionalItem(OptionalItemRequest request) {
        var item = optionalItemRepository.save(request.toEntity());
        return OptionalItemResponse.fromEntity(item);
    }

    public OptionalItemResponse getOptionalItem(Long id) {
        return optionalItemRepository.findById(id)
                .map(OptionalItemResponse::fromEntity)
                .orElseThrow(() -> new DomainException("Item not found", HttpStatus.NOT_FOUND.value()));
    }


    public void deleteOptionalItem(Long id) {
        if (!optionalItemRepository.existsById(id)) {
            throw new DomainException("Item not found", HttpStatus.NOT_FOUND.value());
        }
        optionalItemRepository.deleteById(id);
    }

    public OptionalItemResponse updateOptionalItem(Long id, OptionalItemRequest request) {
        var item = optionalItemRepository.findById(id)
                .orElseThrow(() -> new DomainException("Item not found", HttpStatus.NOT_FOUND.value()));

        item.setPrice(request.price());
        item.setName(request.name());

        return OptionalItemResponse.fromEntity(optionalItemRepository.save(item));
    }

    public List<OptionalItemResponse> getOptionalItems() {
        return optionalItemRepository.findAll()
                .stream()
                .map(OptionalItemResponse::fromEntity)
                .collect(Collectors.toList());
    }


}
