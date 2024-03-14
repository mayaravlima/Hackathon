package com.postech.hackathon.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.postech.hackathon.client.controller.ClientController;
import com.postech.hackathon.client.model.ClientRequest;
import com.postech.hackathon.client.utils.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void createClientTest() throws Exception {
        ClientRequest request = new ClientRequest(
                "John Doe",
                Country.BRAZIL,
                "53242494598",
                "123456789",
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );

        mockMvc.perform(post("/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetClient() throws Exception {
        long clientId = 1L;
        mockMvc.perform(get("/clients/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllClients() throws Exception {
        mockMvc.perform(get("/clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateClient() throws Exception {
        long clientId = 1L;
        ClientRequest clientRequest = new ClientRequest(
                "John Doe",
                Country.BRAZIL,
                "53242494598",
                "123456789",
                LocalDate.now().minusYears(1),
                "Address address",
                "test@email.com",
                "455454545454"
        );

        mockMvc.perform(put("/clients/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clientRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteClient() throws Exception {
        long clientId = 1L;
        mockMvc.perform(delete("/clients/" + clientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}