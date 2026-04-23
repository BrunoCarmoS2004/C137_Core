package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.ServiceProductRepository;
import br.com.c137.project.financial.core.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ServiceProductValidation {
    @Autowired
    private ServiceProductRepository serviceProductRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void serviceProductExistsValidation(UUID id){
        boolean exist = serviceProductRepository.existsById(id);
        if (!exist){
            throw new NotFoundException(messageUtils.getMessage("service.product.not-exists"));
        }
    }

    public void codeExistsValidation(String code){
        boolean exist = serviceProductRepository.existsByCode(code);
        if (exist){
            throw new ValidationException(getCodeExistsMessage());
        }
    }

    public void codeExistsInOtherIdValidation(String code, UUID id){
        boolean exist = serviceProductRepository.existsByCodeAndIdNot(code, id);
        if (exist){
            throw new ValidationException(getCodeExistsMessage());
        }
    }

    private String getCodeExistsMessage(){
        return messageUtils.getMessage("service.product.code.exists");
    }
}
