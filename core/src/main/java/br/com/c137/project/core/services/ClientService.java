package br.com.c137.project.core.services;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.mappers.client.ClientClientToGetMapper;
import br.com.c137.project.core.mappers.client.ClientPostToClientMapper;
import br.com.c137.project.core.mappers.client.ClientPutToClientMapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.partner.Client;
import br.com.c137.project.core.multitenancy.tenant.repositorys.ClientRepository;
import br.com.c137.project.core.validations.ClientValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientValidation clientValidation;
    @Autowired
    private ClientPostToClientMapper clientPostToClientMapper;
    @Autowired
    private ClientClientToGetMapper clientClientToGetMapper;
    @Autowired
    private ClientPutToClientMapper clientPutToClientMapper;

    private final String clientNotFound = "Client not found";

    public ResponseEntity<Page<ClientGetDTO>> getAll(Pageable pageable) {
        Page<ClientGetDTO> clients = clientRepository.findBy(pageable, ClientGetDTO.class);
        if (clients.getTotalElements() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(clients);
    }

    public ResponseEntity<ClientGetDTO> getClientById(UUID id) {
        ClientGetDTO client = clientRepository.findById(id, ClientGetDTO.class).orElseThrow(() -> new NotFoundException(clientNotFound));
        return ResponseEntity.status(HttpStatus.OK).body(client);
    }

    public ResponseEntity<ClientGetDTO> postClient(ClientPostDTO clientPostDTO) {
        clientValidation.inscriptionExistsValidation(clientPostDTO.inscription());
        clientValidation.emailExistsValidation(clientPostDTO.email());
        Client client = clientPostToClientMapper.mapper(clientPostDTO);
        client = clientRepository.save(client);
        ClientGetDTO clientGetDTO = clientClientToGetMapper.mapper(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientGetDTO);
    }

    public ResponseEntity<ClientGetDTO> putClient(UUID id, ClientPutDTO clientPutDTO) {
        clientValidation.inscriptionExistsInOtherIdValidation(clientPutDTO.inscription(), id);
        clientValidation.emailExistsInOtherIdValidation(clientPutDTO.email(), id);
        Client client = clientRepository.findById(id, Client.class).orElseThrow(() -> new NotFoundException(clientNotFound));
        client = clientPutToClientMapper.mapper(clientPutDTO, client);
        client = clientRepository.save(client);
        ClientGetDTO clientGetDTO = clientClientToGetMapper.mapper(client);
        return ResponseEntity.status(HttpStatus.OK).body(clientGetDTO);
    }

    public ResponseEntity<ClientGetDTO> deleteClient(UUID id) {
        clientValidation.clientExistsValidation(id);
        clientRepository.updateEntityStatus(EntityStatus.DELETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ClientGetDTO> inactiveClient(UUID id) {
        clientValidation.clientExistsValidation(id);
        clientRepository.updateEntityStatus(EntityStatus.INACTIVE, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<ClientGetDTO> updateCreationStuatus(UUID id) {
        //TODO, Id client verification already make in the AdressService
        clientRepository.updateCreationStatus(CreationStatus.COMPLETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
