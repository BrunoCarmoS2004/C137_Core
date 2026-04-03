package br.com.c137.project.core.services;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.mappers.ContactMapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ContactGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.ContactPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ContactPutDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.Contact;
import br.com.c137.project.core.multitenancy.tenant.repositorys.ContactRepository;
import br.com.c137.project.core.responses.ResponsePayload;
import br.com.c137.project.core.validations.ContactValidation;
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
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactValidation contactValidation;

    @Autowired
    private ClientService clientService;

    private final String contactNotFoundMessage = "Contact not found";

    private final String contactCreatedMessage = "Contact created";

    private final String contactUpdatedMessage = "Contact updated";

    private final String contactFoundMessage = "Contact found";

    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<ContactGetDTO> assembler) {
        Page<ContactGetDTO> contacts = contactRepository.findBy(pageable, ContactGetDTO.class);
        return pageHasContent(contacts, assembler);
    }

    public ResponseEntity<?> getAllByContactOf(UUID id, Pageable pageable, PagedResourcesAssembler<ContactGetDTO> assembler) {
        Page<ContactGetDTO> contacts = contactRepository.findAllByContactOf(id, pageable);
        return pageHasContent(contacts,  assembler);
    }

    public ResponseEntity<ResponsePayload<ContactGetDTO>> getContactById(UUID id) {
        ContactGetDTO contactGetDTO = contactRepository.findById(id, ContactGetDTO.class).orElseThrow(() -> new NotFoundException(contactNotFoundMessage));
        return createResponse(HttpStatus.OK, contactGetDTO.id(), contactGetDTO, contactFoundMessage);
    }

    public ResponseEntity<ResponsePayload<ContactGetDTO>> postContact(ContactPostDTO contactPostDTO){
        //TODO, TROCAR PARA O SUPPLIER QUANDO FOR SUPPLIER O CREATED OF SUPPLIER
        clientService.clientExistsValidation(contactPostDTO.contactOf());
        Contact contact = contactMapper.postToContact(contactPostDTO);
        contactRepository.save(contact);
        ContactGetDTO contactGetDTO = contactMapper.contactToContactGetDTO(contact);
        return createResponse(HttpStatus.CREATED, contactGetDTO.id(), contactGetDTO, contactCreatedMessage);
    }

    public ResponseEntity<ResponsePayload<ContactGetDTO>> putContact(UUID id, ContactPutDTO contactPutDTO){
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new NotFoundException(contactNotFoundMessage));
        contact = contactMapper.putToContact(contactPutDTO, contact);
        contact = contactRepository.save(contact);
        ContactGetDTO contactGetDTO = contactMapper.contactToContactGetDTO(contact);
        return createResponse(HttpStatus.OK, contactGetDTO.id(), contactGetDTO, contactUpdatedMessage);
    }

    public ResponseEntity<Void> deleteContact(UUID id) {
        contactValidation.contactExistsValidation(id);
        contactRepository.updateEntityStatus(EntityStatus.DELETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> inactiveContact(UUID id) {
        contactValidation.contactExistsValidation(id);
        contactRepository.updateEntityStatus(EntityStatus.INACTIVE, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
