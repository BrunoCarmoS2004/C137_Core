package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.ContactRepository;
import br.com.c137.project.financial.core.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ContactValidation {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void contactExistsValidation(UUID id) {
        boolean exists = contactRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(messageUtils.getMessage("contact.not-exists"));
        }
    }

    public void telephoneExistsInOtherIdValidation(String telephone, UUID id) {
        boolean exists = contactRepository.existsByTelephoneAndIdNot(telephone, id);
        if (exists) {
            throw new ValidationException(getTelephoneExistMessage());
        }
    }

    public void telephoneExistsValidation(String telephone) {
        boolean exists = contactRepository.existsByTelephone(telephone);
        if (exists) {
            throw new ValidationException(getTelephoneExistMessage());
        }
    }

    private String getTelephoneExistMessage() {
        return messageUtils.getMessage("contact.telephone.exists");
    }
}
