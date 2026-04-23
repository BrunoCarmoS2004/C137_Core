package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.SupplierRepository;
import br.com.c137.project.financial.core.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SupplierValidation {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void supplierExistsValidation(UUID id){
        boolean exist = supplierRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(messageUtils.getMessage("supplier.not-exists"));
        }
    }

    public void inscriptionExistsValidation(String inscription){
        boolean exist = supplierRepository.existsByInscription(inscription);
        if (exist){
            throw new ValidationException(getInscriptionExistsMessage());
        }
    }
    public void emailExistsValidation(String email){
        boolean exist = supplierRepository.existsByEmail(email);
        if (exist){
            throw new ValidationException(getEmailExistsMessage());
        }
    }

    public void inscriptionExistsInOtherIdValidation(String inscription, UUID id){
        boolean exist = supplierRepository.existsByInscriptionAndIdNot(inscription, id);
        if (exist){
            throw new ValidationException(getInscriptionExistsMessage());
        }
    }
    public void emailExistsInOtherIdValidation(String email, UUID id){
        boolean exist = supplierRepository.existsByEmailAndIdNot(email, id);
        if (exist){
            throw new ValidationException(getEmailExistsMessage());
        }
    }

    public String getEmailExistsMessage(){
        return messageUtils.getMessage("supplier.email.exists");
    }

    public String getInscriptionExistsMessage(){
        return messageUtils.getMessage("supplier.inscription.exists");
    }
}
