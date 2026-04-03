package br.com.c137.project.core.validations;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.multitenancy.tenant.repositorys.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ContactValidation {

    @Autowired
    private ContactRepository contactRepository;

    private final String contactNotExistMessage = "Contact not exist";

    public void contactExistsValidation(UUID id) {
        boolean exists = contactRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(contactNotExistMessage);
        }
    }
}
