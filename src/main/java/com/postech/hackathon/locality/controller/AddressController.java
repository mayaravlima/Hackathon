package com.postech.hackathon.locality.controller;

import com.postech.hackathon.locality.model.request.AddressRequest;
import com.postech.hackathon.locality.model.response.AddressResponse;
import com.postech.hackathon.locality.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/addresses")
@AllArgsConstructor
public class AddressController {

    private AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable Long id) {
        return ResponseEntity.status(200).body(addressService.getAddressById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @RequestBody AddressRequest addressRequest) {
        return ResponseEntity.ok(addressService.updateAddress(addressRequest, id));
    }
}
