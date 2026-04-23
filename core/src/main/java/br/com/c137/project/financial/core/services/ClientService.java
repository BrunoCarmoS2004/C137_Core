package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.ClientMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.partner.Client;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.ClientRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.utils.MessageUtils;
import br.com.c137.project.financial.core.validations.ClientValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.*;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientValidation clientValidation;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "clients", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<ClientGetDTO> getAll(Pageable pageable) {
        Page<ClientGetDTO> clients = clientRepository.findBy(pageable, ClientGetDTO.class);
        return new PagedModel<>(clients);
    }

    @Cacheable(value = "client", key = "#id")
    public ClientGetDTO getClientById(UUID id) {
        return clientRepository.findById(id, ClientGetDTO.class)
                .orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "clients", allEntries = true)
    public ClientGetDTO postClient(ClientPostDTO clientPostDTO) {
        clientValidation.inscriptionExistsValidation(clientPostDTO.inscription());
        clientValidation.emailExistsValidation(clientPostDTO.email());

        Client client = clientMapper.postToClient(clientPostDTO);
        client = clientRepository.save(client);
        return clientMapper.clientToClientGetDTO(client);
    }

    @Caching(evict = {
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "client", key = "#id")
    })
    public ClientGetDTO putClient(UUID id, ClientPutDTO clientPutDTO) {
        clientValidation.inscriptionExistsInOtherIdValidation(clientPutDTO.inscription(), id);
        clientValidation.emailExistsInOtherIdValidation(clientPutDTO.email(), id);

        Client client = clientRepository.findById(id, Client.class)
                .orElseThrow(() -> new NotFoundException(getNotFoundMessage()));

        client = clientMapper.putToClient(clientPutDTO, client);
        client = clientRepository.save(client);

        return clientMapper.clientToClientGetDTO(client);
    }

    @Caching(evict = {
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "client", key = "#id")
    })
    public void deleteClient(UUID id) {
        clientExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "client", key = "#id")
    })
    public void inactiveClient(UUID id) {
        clientExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
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

    private String getNotFoundMessage(){
        return messageUtils.getMessage("client.not-found");
    }
}




