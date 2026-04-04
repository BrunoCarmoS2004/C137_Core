package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositorys.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ContactValidation {

    @Autowired
    private ContactRepository contactRepository;

    private final String contactNotExistMessage = "Contact not exists";

    private final String telephoneExistMessage = "Telephone already exists";

    public void contactExistsValidation(UUID id) {
        boolean exists = contactRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(contactNotExistMessage);
        }
    }

    public void telephoneExistsInOtherIdValidation(String telephone, UUID id) {
        boolean exists = contactRepository.existsByTelephoneAndIdNot(telephone, id);
        if (exists) {
            throw new ValidationException(telephoneExistMessage);
        }
    }

    public void telephoneExistsValidation(String telephone) {
        boolean exists = contactRepository.existsByTelephone(telephone);
        if (exists) {
            throw new ValidationException(telephoneExistMessage);
        }
    }
}
