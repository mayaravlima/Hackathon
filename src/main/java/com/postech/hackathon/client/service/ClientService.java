package com.postech.hackathon.client.service;

import com.postech.hackathon.client.entity.Client;
import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.client.model.ClientRequest;
import com.postech.hackathon.client.model.ClientResponse;
import com.postech.hackathon.client.repository.ClientRepository;
import com.postech.hackathon.locality.model.request.AddressRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {

    private static final String BRAZIL = "BRAZIL";
    private static final String CLIENT_NOT_FOUND = "Client not found";
    private static final String CPF_ALREADY_REGISTERED = "CPF already registered";
    private static final String PASSPORT_ALREADY_REGISTERED = "Passport number already registered";
    private static final String CPF_REQUIRED = "CPF is required for clients from Brazil";
    private static final String PASSPORT_REQUIRED = "Passport number is required for clients from other countries";
    private final ClientRepository clientRepository;

    public ClientResponse createClient(ClientRequest request) {
        var newClient = request.toEntity(request);
        String countryName = request.originCountry().name();

        if (BRAZIL.equalsIgnoreCase(countryName)) {
            validateBrazilianClient(request);
            newClient.setPassportNumber(null);
        } else {
            validateForeignClient(request);
            newClient.setCpf(null);
        }

        if (clientRepository.existsByEmail(request.email())) {
            throw new DomainException("Email already registered", HttpStatus.BAD_REQUEST.value());
        }

        var client = clientRepository.save(newClient);

        return ClientResponse.fromEntity(client);
    }

    public ClientResponse getClient(Long id) {
        return clientRepository.findById(id)
                .map(ClientResponse::fromEntity)
                .orElseThrow(() -> new DomainException(CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
    }

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ClientResponse updateClient(Long id, ClientRequest request) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new DomainException(CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND.value()));

        updateClientDetails(client, request);

        return ClientResponse.fromEntity(clientRepository.save(client));
    }

    public void deleteClient(Long id) {
        clientRepository.findById(id).orElseThrow(() -> new DomainException(CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND.value()));
        clientRepository.deleteById(id);
    }

    private void validateBrazilianClient(ClientRequest request) {
        if (request.cpf() == null) {
            throw new DomainException("CPF is required for clients from Brazil", HttpStatus.BAD_REQUEST.value());
        }

        if (clientRepository.existsByCpf(request.cpf())) {
            throw new DomainException(CPF_ALREADY_REGISTERED, HttpStatus.BAD_REQUEST.value());
        }
    }

    private void validateForeignClient(ClientRequest request) {
        if (request.passportNumber() == null) {
            throw new DomainException("Passport number is required for clients from other countries", HttpStatus.BAD_REQUEST.value());
        }

        if (clientRepository.existsByPassportNumber(request.passportNumber())) {
            throw new DomainException(PASSPORT_ALREADY_REGISTERED, HttpStatus.BAD_REQUEST.value());
        }
    }

    private void validateBrazilianClient(ClientRequest request, Client client) {
        if (request.cpf() == null) {
            throw new DomainException(CPF_REQUIRED, HttpStatus.BAD_REQUEST.value());
        }

        if (client.getCpf() == null || !client.getCpf().equals(request.cpf())) {
            if (clientRepository.existsByCpf(request.cpf())) {
                throw new DomainException(CPF_ALREADY_REGISTERED, HttpStatus.BAD_REQUEST.value());
            }
        }
    }

    private void validateForeignClient(ClientRequest request, Client client) {
        if (request.passportNumber() == null) {
            throw new DomainException(PASSPORT_REQUIRED, HttpStatus.BAD_REQUEST.value());
        }

        if (client.getPassportNumber() == null || !client.getPassportNumber().equals(request.passportNumber())) {
            if (clientRepository.existsByPassportNumber(request.passportNumber())) {
                throw new DomainException(PASSPORT_ALREADY_REGISTERED, HttpStatus.BAD_REQUEST.value());
            }
        }
    }

    private void validateEmail(ClientRequest request, Client client) {
        if (!client.getEmail().equals(request.email())) {
            if (clientRepository.existsByEmail(request.email())) {
                throw new DomainException("Email already registered", HttpStatus.BAD_REQUEST.value());
            }
        }
    }


    private void updateClientDetails(Client client, ClientRequest request) {
        String countryName = request.originCountry().name();

        if (BRAZIL.equalsIgnoreCase(countryName)) {
            validateBrazilianClient(request, client);
            client.setCpf(request.cpf());
            client.setPassportNumber(null);
        } else {
            validateForeignClient(request, client);
            client.setPassportNumber(request.passportNumber());
            client.setCpf(null);
        }

        validateEmail(request, client);

        client.setName(request.name());
        client.setOriginCountry(request.originCountry());
        client.setDateOfBirth(request.dateOfBirth());
        client.setEmail(request.email());
        client.setPhoneNumber(request.phoneNumber());
    }

    public ClientResponse updateClientAddress(Long id, AddressRequest request) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new DomainException(CLIENT_NOT_FOUND, HttpStatus.NOT_FOUND.value()));


        var newAddress = request.toEntity();
        newAddress.setId(client.getAddress().getId());

        client.setAddress(newAddress);

        return ClientResponse.fromEntity(clientRepository.save(client));
    }
}