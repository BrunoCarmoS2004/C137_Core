package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.AddressRepository;
import br.com.c137.project.financial.core.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressValidation {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void addressExistsValidation(UUID id){
        boolean exists = addressRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(messageUtils.getMessage("address.not-exists"));
        }
    }

    public void zipCodeAndNumberExistsValidation(String zipCode, Integer number){
        boolean exists = addressRepository.existsByZipCodeAndNumber(zipCode, number);
        if (exists){
            throw new ValidationException(getZipCodeAndNuberExistMessage());
        }
    }

    public void zipCodeAndNumberInOtherIdExistsValidation(String zipCode, Integer number, UUID id){
        boolean exists = addressRepository.existsByZipCodeAndNumberAndIdNot(zipCode, number, id);
        if (exists){
            throw new ValidationException(getZipCodeAndNuberExistMessage());
        }
    }

    private String getZipCodeAndNuberExistMessage(){
        return messageUtils.getMessage("address.zip-code-number.exists");
    }
}
