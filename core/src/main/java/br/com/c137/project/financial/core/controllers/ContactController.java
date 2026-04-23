package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ContactGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ContactPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ContactPutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.ContactService;
import br.com.c137.project.financial.core.utils.MessageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.createResponse;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<ContactGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.getAll(pageable));
    }

    @GetMapping("/contactof/{id}")
    public ResponseEntity<PagedModel<ContactGetDTO>> getAllByContactOfId(@PathVariable UUID id, @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.getAllByContactOf(id, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<ContactGetDTO>> getContactById(@PathVariable UUID id) {
        ContactGetDTO contactGetDTO = contactService.getContactById(id);
        return createResponse(
                HttpStatus.OK,
                contactGetDTO.id(),
                contactGetDTO,
                messageUtils.getMessage("contact.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<ContactGetDTO>> postContact(@Valid @RequestBody ContactPostDTO contactPostDTO) {
        ContactGetDTO contactGetDTO = contactService.postContact(contactPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                contactGetDTO.id(),
                contactGetDTO,
                messageUtils.getMessage("contact.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<ContactGetDTO>> putContact(@PathVariable UUID id, @Valid @RequestBody ContactPutDTO contactPutDTO) {
        ContactGetDTO contactGetDTO = contactService.putContact(id, contactPutDTO);
        return createResponse(
                HttpStatus.OK,
                contactGetDTO.id(),
                contactGetDTO,
                messageUtils.getMessage("contact.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable UUID id) {
        contactService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveContact(@PathVariable UUID id) {
        contactService.inactiveContact(id);
        return ResponseEntity.noContent().build();
    }
}
