package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ContactGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ContactPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ContactPutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable, PagedResourcesAssembler<ContactGetDTO> assembler) {
        return contactService.getAll(pageable, assembler);
    }

    @GetMapping("/contactof/{id}")
    public ResponseEntity<?> getAllByContactOfId(@PathVariable UUID id, @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable, PagedResourcesAssembler<ContactGetDTO> assembler) {
        return contactService.getAllByContactOf(id, pageable, assembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<ContactGetDTO>> getContactById(@PathVariable UUID id) {
        return contactService.getContactById(id);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<ContactGetDTO>> postContact(@Valid @RequestBody ContactPostDTO contactPostDTO){
        return contactService.postContact(contactPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<ContactGetDTO>> putContact(@PathVariable UUID id, @Valid @RequestBody ContactPutDTO contactPutDTO){
        return contactService.putContact(id, contactPutDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID id){
        return contactService.deleteContact(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveContact(@PathVariable UUID id){
        return contactService.inactiveContact(id);
    }
}
