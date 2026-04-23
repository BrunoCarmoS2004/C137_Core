package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.ContactMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ContactGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ContactPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ContactPutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.Contact;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.ContactRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.utils.MessageUtils;
import br.com.c137.project.financial.core.validations.ContactValidation;
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

import static br.com.c137.project.financial.core.utils.ServiceUtils.createResponse;

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

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "contacts", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<ContactGetDTO> getAll(Pageable pageable) {
        Page<ContactGetDTO> contacts = contactRepository.findBy(pageable, ContactGetDTO.class);
        return new PagedModel<>(contacts);
    }

    @Cacheable(
            value = "contactsof",
            key = "#id.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public PagedModel<ContactGetDTO> getAllByContactOf(UUID id, Pageable pageable) {
        Page<ContactGetDTO> contacts = contactRepository.findAllByContactOf(id, pageable);
        return new PagedModel<>(contacts);
    }

    @Cacheable(value = "contact", key = "#id")
    public ContactGetDTO getContactById(UUID id) {
        return contactRepository.findById(id, ContactGetDTO.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "contacts", allEntries = true)
    public ContactGetDTO postContact(ContactPostDTO contactPostDTO) {
        contactValidation.telephoneExistsValidation(contactPostDTO.telephone());
        existsCreatedForEntity(contactPostDTO);
        Contact contact = contactMapper.postToContact(contactPostDTO);
        contactRepository.save(contact);
        updateCreatedForEntityStatus(contactPostDTO);
        return contactMapper.contactToContactGetDTO(contact);
    }
    @Caching(evict = {
            @CacheEvict(value = "contacts", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(
                    value = "contactsof",
                    key = "#id.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
            ),
            @CacheEvict(value = "contact", key = "#id")
    })
    public ContactGetDTO putContact(UUID id, ContactPutDTO contactPutDTO) {
        contactValidation.telephoneExistsInOtherIdValidation(contactPutDTO.telephone(), id);
        Contact contact = contactRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        contact = contactMapper.putToContact(contactPutDTO, contact);
        contact = contactRepository.save(contact);
        return contactMapper.contactToContactGetDTO(contact);
    }

    @Caching(evict = {
            @CacheEvict(value = "contacts", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(
                    value = "contactsof",
                    key = "#id.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
            ),
            @CacheEvict(value = "contact", key = "#id")
    })
    public void deleteContact(UUID id) {
        contactValidation.contactExistsValidation(id);
        contactRepository.updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "contacts", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(
                    value = "contactsof",
                    key = "#id.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
            ),
            @CacheEvict(value = "contact", key = "#id")
    })
    public void inactiveContact(UUID id) {
        contactValidation.contactExistsValidation(id);
        contactRepository.updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    private void updateCreatedForEntityStatus(ContactPostDTO contactPostDTO) {
        if (contactPostDTO.createdFor() == CreatedFor.CLIENT) {
            clientService.updateCreationStuatus(contactPostDTO.contactOf());
        } else if (contactPostDTO.createdFor() == CreatedFor.SUPPLIER) {
            supplierService.updateCreationStuatus(contactPostDTO.contactOf());
        }
    }

    private void existsCreatedForEntity(ContactPostDTO contactPostDTO) {
        if (contactPostDTO.createdFor() == CreatedFor.CLIENT) {
            clientService.clientExistsValidation(contactPostDTO.contactOf());
        } else if (contactPostDTO.createdFor() == CreatedFor.SUPPLIER) {
            supplierService.supplierExistsValidation(contactPostDTO.contactOf());
        }
    }

    private String getNotFoundMessage(){
        return messageUtils.getMessage("contact.not-found");
    }
}
