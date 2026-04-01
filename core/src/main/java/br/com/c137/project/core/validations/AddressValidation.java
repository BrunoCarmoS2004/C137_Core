package br.com.c137.project.core.validations;

import br.com.c137.project.core.exceptions.ValidationException;
import br.com.c137.project.core.multitenancy.tenant.repositorys.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AddressValidation {

    @Autowired
    private AddressRepository addressRepository;

    private String addressNotExistMessage = "Address not exist";

    public void addressExistsValidation(UUID id){
        boolean exists = addressRepository.existsById(id);
        if (!exists) {
            throw new ValidationException(addressNotExistMessage);
        }
    }

}
