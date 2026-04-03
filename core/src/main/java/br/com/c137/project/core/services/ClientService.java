package br.com.c137.project.core.services;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.mappers.ClientMapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.partner.Client;
import br.com.c137.project.core.multitenancy.tenant.repositorys.ClientRepository;
import br.com.c137.project.core.responses.ResponsePayload;
import br.com.c137.project.core.validations.ClientValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.c137.project.core.utils.ServiceUtils.createResponse;
import static br.com.c137.project.core.utils.ServiceUtils.pageHasContent;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientValidation clientValidation;

    @Autowired
    private ClientMapper clientMapper;

    private final String clientNotFoundMessage = "Client not found";

    private final String clientCreatedMessage = "Client Created";

    private final String clientFoundMessage = "Client Found";

    private final String clientUpdatedMessage = "Client Updated";

    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<ClientGetDTO> assembler) {
        Page<ClientGetDTO> clients = clientRepository.findBy(pageable, ClientGetDTO.class);
        return pageHasContent(clients, assembler);
    }

    public ResponseEntity<ResponsePayload<ClientGetDTO>> getClientById(UUID id) {
        ClientGetDTO clientGetDTO = clientRepository.findById(id, ClientGetDTO.class).orElseThrow(() -> new NotFoundException(clientNotFoundMessage));
        return createResponse(HttpStatus.OK, clientGetDTO.id(), clientGetDTO, clientFoundMessage);
    }

    public ResponseEntity<ResponsePayload<ClientGetDTO>> postClient(ClientPostDTO clientPostDTO) {
        clientValidation.inscriptionExistsValidation(clientPostDTO.inscription());
        clientValidation.emailExistsValidation(clientPostDTO.email());
        Client client = clientMapper.postToClient(clientPostDTO);
        client = clientRepository.save(client);
        ClientGetDTO clientGetDTO = clientMapper.clientToClientGetDTO(client);
        return createResponse(HttpStatus.CREATED, clientGetDTO.id(), clientGetDTO, clientCreatedMessage);
    }

    public ResponseEntity<ResponsePayload<ClientGetDTO>> putClient(UUID id, ClientPutDTO clientPutDTO) {
        clientValidation.inscriptionExistsInOtherIdValidation(clientPutDTO.inscription(), id);
        clientValidation.emailExistsInOtherIdValidation(clientPutDTO.email(), id);
        Client client = clientRepository.findById(id, Client.class).orElseThrow(() -> new NotFoundException(clientNotFoundMessage));
        client = clientMapper.putToClient(clientPutDTO, client);
        client = clientRepository.save(client);
        ClientGetDTO clientGetDTO = clientMapper.clientToClientGetDTO(client);
        return createResponse(HttpStatus.OK, clientGetDTO.id(), clientGetDTO, clientUpdatedMessage);
    }

    public ResponseEntity<Void> deleteClient(UUID id) {
        clientExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> inactiveClient(UUID id) {
        clientExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected void updateCreationStuatus(UUID id) {
        clientRepository.updateCreationStatus(CreationStatus.COMPLETED, id);
    }

    protected void clientExistsValidation(UUID id) {
        clientValidation.clientExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        clientRepository.updateEntityStatus(entityStatus, id);
    }
}
