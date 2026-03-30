package br.com.c137.project.core.validations;

import br.com.c137.project.core.multitenancy.tenant.repositorys.ClientRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientValidation {
    @Autowired
    private ClientRepository clientRepository;

    public void clientExistsValidation(UUID id){
        boolean exist = clientRepository.existsById(id);
        if (!exist){
            throw new ValidationException("Client not exists");
        }
    }

    public void inscriptionExistsInOtherIdValidation(String inscription, UUID id){
        boolean exist = clientRepository.existsByInscriptionAndIdNot(inscription, id);
        if (exist){
            throw new ValidationException("Client inscription already exists");
        }
    }
    public void emailExistsInOtherIdValidation(String email, UUID id){
        boolean exist = clientRepository.existsByEmailAndIdNot(email, id);
        if (exist){
            throw new ValidationException("Client email already exists");
        }
    }

    public void inscriptionExistsValidation(String inscription){
        boolean exist = clientRepository.existsByInscription(inscription);
        if (exist){
            throw new ValidationException("Client inscription already exists");
        }
    }
    public void emailExistsValidation(String email){
        boolean exist = clientRepository.existsByEmail(email);
        if (exist){
            throw new ValidationException("Client email already exists");
        }
    }
}
