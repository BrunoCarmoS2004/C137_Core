package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable,
            PagedResourcesAssembler<ClientGetDTO> assembler) {
        return clientService.getAll(pageable, assembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<ClientGetDTO>> getClientById(@PathVariable UUID id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<ClientGetDTO>> postClient(@Valid @RequestBody ClientPostDTO clientPostDTO) {
        return clientService.postClient(clientPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<ClientGetDTO>> putClient(@PathVariable UUID id, @Valid @RequestBody ClientPutDTO clientPutDTO) {
        return clientService.putClient(id, clientPutDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable UUID id) {
        return clientService.deleteClient(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveClient(@PathVariable UUID id) {
        return clientService.inactiveClient(id);
    }
}
