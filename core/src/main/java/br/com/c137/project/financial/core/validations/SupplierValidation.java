package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositorys.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SupplierValidation {
    @Autowired
    private SupplierRepository supplierRepository;

    private final String supplierNotExistMessage = "Supplier not exists";

    private final String inscriptionExistsMessage = "Supplier inscription already exists";

    private final String emailExistsMessage = "Supplier email already exists";

    public void supplierExistsValidation(UUID id){
        boolean exist = supplierRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(supplierNotExistMessage);
        }
    }

    public void inscriptionExistsValidation(String inscription){
        boolean exist = supplierRepository.existsByInscription(inscription);
        if (exist){
            throw new ValidationException(inscriptionExistsMessage);
        }
    }
    public void emailExistsValidation(String email){
        boolean exist = supplierRepository.existsByEmail(email);
        if (exist){
            throw new ValidationException(emailExistsMessage);
        }
    }

    public void inscriptionExistsInOtherIdValidation(String inscription, UUID id){
        boolean exist = supplierRepository.existsByInscriptionAndIdNot(inscription, id);
        if (exist){
            throw new ValidationException(inscriptionExistsMessage);
        }
    }
    public void emailExistsInOtherIdValidation(String email, UUID id){
        boolean exist = supplierRepository.existsByEmailAndIdNot(email, id);
        if (exist){
            throw new ValidationException(emailExistsMessage);
        }
    }
}
