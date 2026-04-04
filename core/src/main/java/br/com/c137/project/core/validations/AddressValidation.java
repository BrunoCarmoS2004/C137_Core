package br.com.c137.project.core.validations;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.exceptions.ValidationException;
import br.com.c137.project.core.multitenancy.tenant.repositorys.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressValidation {

    @Autowired
    private AddressRepository addressRepository;

    private String addressNotExistMessage = "Address not exists";

    private String zipCodeAndNuberExistMessage = "Zip code and number already exists";

    public void addressExistsValidation(UUID id){
        boolean exists = addressRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(addressNotExistMessage);
        }
    }

    public void zipCodeAndNumberExistsValidation(String zipCode, Integer number){
        boolean exists = addressRepository.existsByZipCodeAndNumber(zipCode, number);
        if (exists){
            throw new ValidationException(zipCodeAndNuberExistMessage);
        }
    }

    public void zipCodeAndNumberInOtherIdExistsValidation(String zipCode, Integer number, UUID id){
        boolean exists = addressRepository.existsByZipCodeAndNumberAndIdNot(zipCode, number, id);
        if (exists){
            throw new ValidationException(zipCodeAndNuberExistMessage);
        }
    }
}
