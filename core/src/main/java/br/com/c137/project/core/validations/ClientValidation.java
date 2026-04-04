package br.com.c137.project.core.validations;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.exceptions.ValidationException;
import br.com.c137.project.core.multitenancy.tenant.repositorys.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientValidation {
    @Autowired
    private ClientRepository clientRepository;

    private final String clientNotExistMessage = "Client not exists";

    private final String inscriptionExistsMessage = "Client inscription already exists";

    private final String emailExistsMessage = "Client email already exists";

    public void clientExistsValidation(UUID id){
        boolean exist = clientRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(clientNotExistMessage);
        }
    }

    public void inscriptionExistsValidation(String inscription){
        boolean exist = clientRepository.existsByInscription(inscription);
        if (exist){
            throw new ValidationException(inscriptionExistsMessage);
        }
    }
    public void emailExistsValidation(String email){
        boolean exist = clientRepository.existsByEmail(email);
        if (exist){
            throw new ValidationException(emailExistsMessage);
        }
    }

    public void inscriptionExistsInOtherIdValidation(String inscription, UUID id){
        boolean exist = clientRepository.existsByInscriptionAndIdNot(inscription, id);
        if (exist){
            throw new ValidationException(inscriptionExistsMessage);
        }
    }
    public void emailExistsInOtherIdValidation(String email, UUID id){
        boolean exist = clientRepository.existsByEmailAndIdNot(email, id);
        if (exist){
            throw new ValidationException(emailExistsMessage);
        }
    }
}
