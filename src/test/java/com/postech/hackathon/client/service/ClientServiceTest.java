package com.postech.hackathon.client.service;

import com.postech.hackathon.client.entity.Client;
import com.postech.hackathon.exception.DomainException;
import com.postech.hackathon.client.model.ClientRequest;
import com.postech.hackathon.client.model.ClientResponse;
import com.postech.hackathon.client.repository.ClientRepository;
import com.postech.hackathon.client.utils.Country;
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



}