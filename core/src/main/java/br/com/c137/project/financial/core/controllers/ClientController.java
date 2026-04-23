package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.ClientService;
import br.com.c137.project.financial.core.utils.MessageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.createResponse;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<ClientGetDTO>> getAll(
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<ClientGetDTO>> getClientById(@PathVariable UUID id) {
        ClientGetDTO clientGetDTO = clientService.getClientById(id);
        return createResponse(
                HttpStatus.OK,
                clientGetDTO.id(),
                clientGetDTO,
                messageUtils.getMessage("client.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<ClientGetDTO>> postClient(@Valid @RequestBody ClientPostDTO clientPostDTO) {
        ClientGetDTO clientGetDTO = clientService.postClient(clientPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                clientGetDTO.id(),
                clientGetDTO,
                messageUtils.getMessage("client.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<ClientGetDTO>> putClient(@PathVariable UUID id, @Valid @RequestBody ClientPutDTO clientPutDTO) {
        ClientGetDTO clientGetDTO = clientService.putClient(id, clientPutDTO);
        return createResponse(
                HttpStatus.OK,
                clientGetDTO.id(),
                clientGetDTO,
                messageUtils.getMessage("client.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveClient(@PathVariable UUID id) {
        clientService.inactiveClient(id);
        return ResponseEntity.noContent().build();
    }
}
