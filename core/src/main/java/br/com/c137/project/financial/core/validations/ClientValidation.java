package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.ClientRepository;
import br.com.c137.project.financial.core.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientValidation {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void clientExistsValidation(UUID id){
        boolean exist = clientRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(messageUtils.getMessage("client.not-exists"));
        }
    }

    public void inscriptionExistsValidation(String inscription){
        boolean exist = clientRepository.existsByInscription(inscription);
        if (exist){
            throw new ValidationException(getInscriptionExistsMessage());
        }
    }
    public void emailExistsValidation(String email){
        boolean exist = clientRepository.existsByEmail(email);
        if (exist){
            throw new ValidationException(getEmailExistsMessage());
        }
    }

    public void inscriptionExistsInOtherIdValidation(String inscription, UUID id){
        boolean exist = clientRepository.existsByInscriptionAndIdNot(inscription, id);
        if (exist){
            throw new ValidationException(getInscriptionExistsMessage());
        }
    }
    public void emailExistsInOtherIdValidation(String email, UUID id){
        boolean exist = clientRepository.existsByEmailAndIdNot(email, id);
        if (exist){
            throw new ValidationException(getEmailExistsMessage());
        }
    }

    public String getEmailExistsMessage(){
        return messageUtils.getMessage("client.email.exists");
    }

    public String getInscriptionExistsMessage(){
        return messageUtils.getMessage("client.inscription.exists");
    }
}
