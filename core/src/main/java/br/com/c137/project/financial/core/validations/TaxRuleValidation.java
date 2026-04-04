package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositorys.TaxRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TaxRuleValidation {
    @Autowired
    private TaxRuleRepository taxRuleRepository;

    private String taxRuleNotExistMessage = "Tax rule not exists";

    private String serviceCodeExistMessage = "Service code already exists";

    public void taxRuleExistsValidation(UUID id){
        boolean exists = taxRuleRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(taxRuleNotExistMessage);
        }
    }

    public void serviceCodeExistValidation(String serviceCode){
        boolean exists = taxRuleRepository.existsByServiceCode(serviceCode);
        if (exists){
            throw new ValidationException(serviceCodeExistMessage);
        }
    }

    public void serviceCodeExistInOtherIdValidation(String serviceCode, UUID id){
        boolean exists = taxRuleRepository.existsByServiceCodeAndIdNot(serviceCode, id);
        if (exists){
            throw new ValidationException(serviceCodeExistMessage);
        }
    }
}
