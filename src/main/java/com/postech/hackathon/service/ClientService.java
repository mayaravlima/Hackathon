package com.postech.hackathon.service;


import com.postech.hackathon.exception.ClientException;
import com.postech.hackathon.model.ClientRequest;
import com.postech.hackathon.model.ClientResponse;
import com.postech.hackathon.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClientService {

    private static final String BRAZIL = "BRAZIL";

    private final ClientRepository clientRepository;

    public ClientResponse createClient(ClientRequest request) {
        String countryName = request.originCountry().name();

        if (BRAZIL.equalsIgnoreCase(countryName)) {
            validateBrazilianClient(request);
        } else {
            validateForeignClient(request);
        }

        var client = clientRepository.save(request.fromDTO(request));

        return ClientResponse.fromEntity(client);
    }

    public ClientResponse getClient(Long id) {
        return clientRepository.findById(id)
                .map(ClientResponse::fromEntity)
                .orElseThrow(() -> new ClientException("Client not found", HttpStatus.NOT_FOUND.value()));
    }

    public List<ClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ClientResponse updateClient(Long id, ClientRequest request) {
        var client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientException("Client not found", HttpStatus.NOT_FOUND.value()));

        String countryName = request.originCountry().name();

        //TODO ajustar validação de CPF e passaporte
        if (!countryName.equals(client.getOriginCountry())){
            if (BRAZIL.equalsIgnoreCase(countryName)) {
                if (client.getCpf() != null && !client.getCpf().equals(request.cpf()) && clientRepository.existsByCpf(request.cpf())) {
                    throw new ClientException("CPF already registered", HttpStatus.BAD_REQUEST.value());
                }
            } else {
                if (client.getPassportNumber() != null && !client.getPassportNumber().equals(request.passportNumber()) && clientRepository.existsByPassportNumber(request.passportNumber())) {
                    throw new ClientException("Passport number already registered", HttpStatus.BAD_REQUEST.value());
                }
            }
        }

        client.setName(request.name());
        client.setOriginCountry(request.originCountry().name());
        client.setCpf(request.cpf());
        client.setPassportNumber(request.passportNumber());
        client.setDateOfBirth(request.dateOfBirth());
        client.setAddress(request.address());
        client.setEmail(request.email());
        client.setPhoneNumber(request.phoneNumber());

        return ClientResponse.fromEntity(clientRepository.save(client));
    }

    public void deleteClient(Long id) {
        clientRepository.findById(id).orElseThrow(() -> new ClientException("Client not found", HttpStatus.NOT_FOUND.value()));
        clientRepository.deleteById(id);
    }


    private void validateBrazilianClient(ClientRequest request) {
        if (request.cpf() == null) {
            throw new ClientException("CPF is required for clients from Brazil", HttpStatus.BAD_REQUEST.value());
        }

        if (clientRepository.existsByCpf(request.cpf())) {
            throw new ClientException("CPF already registered", HttpStatus.BAD_REQUEST.value());
        }
    }

    private void validateForeignClient(ClientRequest request) {
        if (request.passportNumber() == null) {
            throw new ClientException("Passport number is required for clients from other countries", HttpStatus.BAD_REQUEST.value());
        }

        if (clientRepository.existsByPassportNumber(request.passportNumber())) {
            throw new ClientException("Passport number already registered", HttpStatus.BAD_REQUEST.value());
        }
    }
}
