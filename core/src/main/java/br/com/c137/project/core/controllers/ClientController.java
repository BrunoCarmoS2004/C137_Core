package br.com.c137.project.core.controllers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.core.responses.ClientResponse;
import br.com.c137.project.core.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return clientService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable UUID id) {
        return clientService.getClientById(id);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> postClient(@Valid @RequestBody ClientPostDTO clientPostDTO) {
        return clientService.postClient(clientPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> putClient(@PathVariable UUID id, @Valid @RequestBody ClientPutDTO clientPutDTO) {
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
