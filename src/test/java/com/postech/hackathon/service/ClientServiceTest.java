package com.postech.hackathon.service;

import com.postech.hackathon.entity.Client;
import com.postech.hackathon.exception.ClientException;
import com.postech.hackathon.model.ClientRequest;
import com.postech.hackathon.model.ClientResponse;
import com.postech.hackathon.repository.ClientRepository;
import com.postech.hackathon.utils.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBRClient() {
        ClientRequest clientRequest = new ClientRequest(
                "John Doe",
                Country.BRAZIL,
                "53242494598",
                null,
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );

        Client client = new Client(
                1L,
                "John Doe",
                Country.BRAZIL,
                "53242494598",
                null,
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );

        when(clientRepository.save(any())).thenReturn(client);
        when(clientRepository.existsByEmail(any())).thenReturn(false);

        ClientResponse result = clientService.createClient(clientRequest);

        ClientResponse expectedResponse = new ClientResponse(
                client.getId(),
                client.getName(),
                client.getOriginCountry(),
                client.getCpf(),
                client.getPassportNumber(),
                client.getDateOfBirth(),
                client.getAddress(),
                client.getEmail(),
                client.getPhoneNumber()
        );

        assertEquals(expectedResponse, result);
    }

    @Test
    public void testCreateNotBRClient() {
        ClientRequest clientRequest = new ClientRequest(
                "John Doe",
                Country.ARGENTINA,
                null,
                "123456789",
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );

        Client client = new Client(
                1L,
                "John Doe",
                Country.ARGENTINA,
                null,
                "123456789",
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );

        when(clientRepository.save(any())).thenReturn(client);

        ClientResponse result = clientService.createClient(clientRequest);

        ClientResponse expectedResponse = new ClientResponse(
                client.getId(),
                client.getName(),
                client.getOriginCountry(),
                client.getCpf(),
                client.getPassportNumber(),
                client.getDateOfBirth(),
                client.getAddress(),
                client.getEmail(),
                client.getPhoneNumber()
        );

        assertEquals(expectedResponse, result);
    }

    @Test
    public void testCreateClientWithEmailExist() {
        ClientRequest clientRequest = new ClientRequest(
                "John Doe",
                Country.ARGENTINA,
                null,
                "123456789",
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );

        when(clientRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(ClientException.class, () -> clientService.createClient(clientRequest));
    }

    @Test
    public void testGetClient() {
        Long clientId = 1L;

        ClientResponse clientResponse = new ClientResponse(
                1L,
                "John Doe",
                Country.BRAZIL,
                "53242494598",
                "123456789",
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );


        Client client = new Client(
                1L,
                "John Doe",
                Country.BRAZIL,
                "53242494598",
                "123456789",
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );

        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        ClientResponse result = clientService.getClient(clientId);

        assertEquals(clientResponse, result);
    }

    @Test
    public void testGetClientNotFound() {
        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(ClientException.class, () -> clientService.getClient(clientId));
    }

}