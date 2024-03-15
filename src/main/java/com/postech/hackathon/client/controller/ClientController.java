package com.postech.hackathon.client.controller;


import com.postech.hackathon.client.model.ClientRequest;
import com.postech.hackathon.client.model.ClientResponse;
import com.postech.hackathon.client.service.ClientService;
import com.postech.hackathon.locality.model.request.AddressRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> createClient(@Valid @RequestBody ClientRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClient(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getClient(id));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponse>> getAllClients() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAllClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable Long id, @Valid @RequestBody ClientRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClient(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}/address")
    public ResponseEntity<ClientResponse> updateClientAddress(@PathVariable Long id, @Valid @RequestBody AddressRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.updateClientAddress(id, request));
    }


}
