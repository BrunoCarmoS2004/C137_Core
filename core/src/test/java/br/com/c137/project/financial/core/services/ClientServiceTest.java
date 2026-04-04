package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.ClientMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.InscriptionType;
import br.com.c137.project.financial.core.multitenancy.tenant.models.partner.Client;
import br.com.c137.project.financial.core.multitenancy.tenant.repositorys.ClientRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.validations.ClientValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//TODO, COLOCAR EM INGLES
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientValidation clientValidation;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PagedResourcesAssembler<ClientGetDTO> assembler;

    @InjectMocks
    private ClientService clientService;

    private UUID clientId;
    private ClientPostDTO clientPostDTO;
    private Client clientEntity;
    private ClientGetDTO clientGetDTO;

    @BeforeEach
    void setUp() {
        clientId = UUID.randomUUID();
        clientPostDTO = new ClientPostDTO(
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
                "IM-554433"
        );
        clientEntity = new Client();
        clientGetDTO = new ClientGetDTO(
                clientId,
                "Global Logistics S.A.",
                "22.111.333/0001-44",
                InscriptionType.CNPJ,
                LocalDate.of(2022, 1, 10),
                "admin@globallogistics.com",
                "(21) 3344-5566",
                "(21) 99888-7766",
                "CONT-9988",
                "IE-88776655",
                LocalDate.of(2022, 1, 15),
                "IM-223344",
                LocalDateTime.now(),
                CreationStatus.COMPLETED,
                EntityStatus.ACTIVE
        );
    }

    @Test
    @DisplayName("Deve salvar um cliente com sucesso quando validações passarem")
    void postClient_ShouldReturnCreated_WhenValidData() {
        // Arrange
        when(clientMapper.postToClient(clientPostDTO)).thenReturn(clientEntity);
        when(clientRepository.save(any(Client.class))).thenReturn(clientEntity);
        when(clientMapper.clientToClientGetDTO(clientEntity)).thenReturn(clientGetDTO);

        // Act
        ResponseEntity<ResponsePayload<ClientGetDTO>> response = clientService.postClient(clientPostDTO);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(clientId);

        // Verifica se as regras de negócio de unicidade foram chamadas
        verify(clientValidation).inscriptionExistsValidation(clientPostDTO.inscription());
        verify(clientValidation).emailExistsValidation(clientPostDTO.email());
        verify(clientRepository).save(clientEntity);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar ID inexistente")
    void getClientById_ShouldThrowNotFound_WhenIdDoesNotExist() {
        // Arrange
        when(clientRepository.findById(clientId, ClientGetDTO.class)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> clientService.getClientById(clientId))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Client not found");
    }

    @Test
    @DisplayName("Deve retornar No Content quando a página de busca estiver vazia")
    void getAll_ShouldReturnNoContent_WhenEmpty() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(clientRepository.findBy(pageable, ClientGetDTO.class)).thenReturn(Page.empty());

        // Act
        ResponseEntity<?> response = clientService.getAll(pageable, assembler);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}