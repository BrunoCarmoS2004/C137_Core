package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositorys.ServiceProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServiceProductValidation {
    @Autowired
    private ServiceProductRepository serviceProductRepository;

    private final String serviceProductNotExistMessage = "Service/Product not exists";

    private final String codeExistsMessage = "Service/Product code already exists";


    public void serviceProductExistsValidation(UUID id){
        boolean exist = serviceProductRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(serviceProductNotExistMessage);
        }
    }

    public void codeExistsValidation(String code){
        boolean exist = serviceProductRepository.existsByCode(code);
        if (exist){
            throw new ValidationException(codeExistsMessage);
        }
    }

    public void codeExistsInOtherIdValidation(String code, UUID id){
        boolean exist = serviceProductRepository.existsByCodeAndIdNot(code, id);
        if (exist){
            throw new ValidationException(codeExistsMessage);
        }
    }
}
