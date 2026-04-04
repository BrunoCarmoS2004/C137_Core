package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.InscriptionType;
import br.com.c137.project.financial.core.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /client - Deve retornar 201 Created")
    void postClient_Endpoint_ShouldReturnStatusCreated() throws Exception {
        ClientPostDTO dto = new ClientPostDTO(
                "Tech Solutions Ltda",
                "12.345.678/0001-90",
                InscriptionType.CNPJ,
                LocalDate.of(2023, 5, 15),
                "contato@techsolutions.com.br",
                "(11) 4002-8922",
                "(11) 98765-4321",
                "ACC-123456",
                "987.654.321.110",
                LocalDate.of(2023, 6, 20),
                "IM-554433");

        // Simula o retorno do Service
        when(clientService.postClient(any(ClientPostDTO.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET /client/{id} - Deve retornar 200 OK")
    void getClientById_Endpoint_ShouldReturnStatusOk() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(get("/client/{id}", id))
                .andExpect(status().isOk());

        verify(clientService).getClientById(id);
    }
}