package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.TaxRuleRepository;
import br.com.c137.project.financial.core.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaxRuleValidation {
    @Autowired
    private TaxRuleRepository taxRuleRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void taxRuleExistsValidation(UUID id){
        boolean exists = taxRuleRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(messageUtils.getMessage("tax.rule.not-exists"));
        }
    }

    public void serviceCodeExistValidation(String serviceCode){
        boolean exists = taxRuleRepository.existsByServiceCode(serviceCode);
        if (exists){
            throw new ValidationException(getServiceCodeExistMessage());
        }
    }

    public void serviceCodeExistInOtherIdValidation(String serviceCode, UUID id){
        boolean exists = taxRuleRepository.existsByServiceCodeAndIdNot(serviceCode, id);
        if (exists){
            throw new ValidationException(getServiceCodeExistMessage());
        }
    }

    private String getServiceCodeExistMessage(){
        return messageUtils.getMessage("tax.rule.service-code.exists");
    }
}
