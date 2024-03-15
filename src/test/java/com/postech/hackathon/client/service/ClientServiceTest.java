package com.postech.hackathon.client.service;

import com.postech.hackathon.client.entity.Client;
import com.postech.hackathon.client.model.ClientRequest;
import com.postech.hackathon.client.model.ClientResponse;
import com.postech.hackathon.client.repository.ClientRepository;
import com.postech.hackathon.client.utils.Country;
import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.locality.entity.Address;
import com.postech.hackathon.locality.model.request.AddressRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createClient_withValidBrazilianClient_returnsClientResponse() {

        ClientRequest request = new ClientRequest(
                "name",
                Country.BRAZIL,
                "12345678901",
                null,
                LocalDate.now().minusYears(5),
                new AddressRequest(
                        "street",
                        "number",
                        "complement",
                        "neighborhood",
                        "city",
                        "state",
                        "country",
                        "zipCode"

                ),
                "test@email.com",
                "123546545"
        );

        when(clientRepository.existsByEmail(any())).thenReturn(false);
        when(clientRepository.existsByCpf(any())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(
                Client
                        .builder()
                        .id(1L)
                        .name("name")
                        .originCountry(Country.BRAZIL)
                        .cpf("12345678901")
                        .dateOfBirth(LocalDate.now().minusYears(5))
                        .address(
                                com.postech.hackathon.locality.entity.Address
                                        .builder()
                                        .id(1L)
                                        .street("street")
                                        .number("number")
                                        .complement("complement")
                                        .neighborhood("neighborhood")
                                        .city("city")
                                        .state("state")
                                        .country("country")
                                        .zipcode("zipCode")
                                        .build()
                        )
                        .email("test@email.com")
                        .phoneNumber("123546545")
                        .build()
        );

        ClientResponse response = clientService.createClient(request);

        assertNotNull(response);
    }

    @Test
    public void createClient_withValidForeignClient_returnsClientResponse() {
        ClientRequest request = new ClientRequest(
                "name",
                Country.ARGENTINA,
                null,
                "AE4545454",
                LocalDate.now().minusYears(5),
                new AddressRequest(
                        "street",
                        "number",
                        "complement",
                        "neighborhood",
                        "city",
                        "state",
                        "country",
                        "zipCode"

                ),
                "test@email.com",
                "123546545"
        );

        when(clientRepository.existsByEmail(any())).thenReturn(false);
        when(clientRepository.existsByCpf(any())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(
                Client
                        .builder()
                        .id(1L)
                        .name("name")
                        .originCountry(Country.ARGENTINA)
                        .passportNumber("AE4545454")
                        .dateOfBirth(LocalDate.now().minusYears(5))
                        .address(
                                com.postech.hackathon.locality.entity.Address
                                        .builder()
                                        .id(1L)
                                        .street("street")
                                        .number("number")
                                        .complement("complement")
                                        .neighborhood("neighborhood")
                                        .city("city")
                                        .state("state")
                                        .country("country")
                                        .zipcode("zipCode")
                                        .build()
                        )
                        .email("test@email.com")
                        .phoneNumber("123546545")
                        .build()
        );

        ClientResponse response = clientService.createClient(request);

        assertNotNull(response);
    }

    @Test
    public void createClient_withExistingEmail_throwsDomainException() {
        ClientRequest request = new ClientRequest(
                "name",
                Country.ARGENTINA,
                null,
                "AE4545454",
                LocalDate.now().minusYears(5),
                new AddressRequest(
                        "street",
                        "number",
                        "complement",
                        "neighborhood",
                        "city",
                        "state",
                        "country",
                        "zipCode"

                ),
                "test@email.com",
                "123546545"
        );
        when(clientRepository.existsByEmail(anyString())).thenReturn(true);

        DomainException exception = assertThrows(DomainException.class, () -> clientService.createClient(request));
        assertEquals("Email already registered", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
    }

    @Test
    public void createClient_withExistingCpf_throwsDomainException() {
        ClientRequest request = new ClientRequest(
                "name",
                Country.BRAZIL,
                "12456645646",
                null,
                LocalDate.now().minusYears(5),
                new AddressRequest(
                        "street",
                        "number",
                        "complement",
                        "neighborhood",
                        "city",
                        "state",
                        "country",
                        "zipCode"

                ),
                "test@email.com",
                "123546545"
        );
        when(clientRepository.existsByCpf(anyString())).thenReturn(true);

        DomainException exception = assertThrows(DomainException.class, () -> clientService.createClient(request));
        assertEquals("CPF already registered", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
    }

    @Test
    public void createClient_withExistingPassportNumber_throwsDomainException() {
        ClientRequest request = new ClientRequest(
                "name",
                Country.ARGENTINA,
                null,
                "AE4545454",
                LocalDate.now().minusYears(5),
                new AddressRequest(
                        "street",
                        "number",
                        "complement",
                        "neighborhood",
                        "city",
                        "state",
                        "country",
                        "zipCode"

                ),
                "test@email.com",
                "123546545"
        );
        when(clientRepository.existsByPassportNumber(anyString())).thenReturn(true);

        DomainException exception = assertThrows(DomainException.class, () -> clientService.createClient(request));
        assertEquals("Passport number already registered", exception.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), exception.getStatus());
    }

    @Test
    public void getClient_withValidId_returnsClientResponse() {
        Long id = 1L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(Client
                .builder()
                .id(1L)
                .name("name")
                .originCountry(Country.ARGENTINA)
                .passportNumber("AE4545454")
                .dateOfBirth(LocalDate.now().minusYears(5))
                .address(
                        com.postech.hackathon.locality.entity.Address
                                .builder()
                                .id(1L)
                                .street("street")
                                .number("number")
                                .complement("complement")
                                .neighborhood("neighborhood")
                                .city("city")
                                .state("state")
                                .country("country")
                                .zipcode("zipCode")
                                .build()
                )
                .email("test@email.com")
                .phoneNumber("123546545")
                .build()));

        ClientResponse response = clientService.getClient(id);

        assertNotNull(response);
    }

    @Test
    public void getClient_withInvalidId_throwsDomainException() {
        Long id = 1L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> clientService.getClient(id));
    }

    @Test
    public void updateClient_withValidIdAndRequest_updatesClientDetails() {
        Long id = 1L;
        ClientRequest request = new ClientRequest(
                "name",
                Country.ARGENTINA,
                null,
                "AE4545454",
                LocalDate.now().minusYears(5),
                new AddressRequest(
                        "street",
                        "number",
                        "complement",
                        "neighborhood",
                        "city",
                        "state",
                        "country",
                        "zipCode"

                ),
                "test@email.com",
                "123546545"
        );
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(Client
                .builder()
                .id(1L)
                .email("test@email.com")
                .address(
                        Address
                                .builder()
                                .id(1L)
                                .build()
                )
                .build()));

        when(clientRepository.save(any(Client.class))).thenReturn(Client
                .builder()
                .id(1L)
                .name("name")
                .originCountry(Country.ARGENTINA)
                .passportNumber("AE4545454")
                .dateOfBirth(LocalDate.now().minusYears(5))
                .address(
                        Address
                                .builder()
                                .id(1L)
                                .street("street")
                                .number("number")
                                .complement("complement")
                                .neighborhood("neighborhood")
                                .city("city")
                                .state("state")
                                .country("country")
                                .zipcode("zipCode")
                                .build()
                )
                .email("test@email.com")
                .phoneNumber("123546545")
                .build());
        ClientResponse response = clientService.updateClient(id, request);

        assertNotNull(response);
    }

    @Test
    public void updateClient_withInvalidId_throwsDomainException() {
        Long id = 1L;
        ClientRequest request = new ClientRequest(
                "name",
                Country.ARGENTINA,
                null,
                "AE4545454",
                LocalDate.now().minusYears(5),
                new AddressRequest(
                        "street",
                        "number",
                        "complement",
                        "neighborhood",
                        "city",
                        "state",
                        "country",
                        "zipCode"

                ),
                "test@email.com",
                "123546545"
        );
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> clientService.updateClient(id, request));
    }

    @Test
    public void deleteClient_withValidId_deletesClient() {
        Long id = 1L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(new Client()));

        clientService.deleteClient(id);

        verify(clientRepository, times(1)).deleteById(id);
    }

    @Test
    public void deleteClient_withInvalidId_throwsDomainException() {
        Long id = 1L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> clientService.deleteClient(id));
    }

    @Test
    public void updateClientAddress_withValidIdAndRequest_updatesClientAddress() {
        Long id = 1L;
        AddressRequest request = new AddressRequest(
                "Updated Street",
                "number",
                "complement",
                "neighborhood",
                "city",
                "state",
                "country",
                "zipCode"
        );

        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(
                Client
                        .builder()
                        .id(1L)
                        .address(
                                Address
                                        .builder()
                                        .id(1L)
                                        .build()
                        )
                        .build()));
        when(clientRepository.save(any(Client.class))).thenReturn(Client
                .builder()
                .id(1L)
                .name("name")
                .originCountry(Country.ARGENTINA)
                .passportNumber("AE4545454")
                .dateOfBirth(LocalDate.now().minusYears(5))
                .address(
                        Address
                                .builder()
                                .id(1L)
                                .street("street")
                                .number("number")
                                .complement("complement")
                                .neighborhood("neighborhood")
                                .city("city")
                                .state("state")
                                .country("country")
                                .zipcode("zipCode")
                                .build()
                )
                .email("test@email.com")
                .phoneNumber("123546545")
                .build());

        ClientResponse response = clientService.updateClientAddress(id, request);

        assertNotNull(response);
    }

    @Test
    public void updateClientAddress_withInvalidId_throwsDomainException() {

        Long id = 1L;
        AddressRequest request = new AddressRequest(
                "Updated Street",
                "number",
                "complement",
                "neighborhood",
                "city",
                "state",
                "country",
                "zipCode"
        );
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DomainException.class, () -> clientService.updateClientAddress(id, request));
    }
}