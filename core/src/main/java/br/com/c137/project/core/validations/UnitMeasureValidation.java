package br.com.c137.project.core.validations;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.exceptions.ValidationException;
import br.com.c137.project.core.multitenancy.tenant.repositorys.UnitMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UnitMeasureValidation {
    @Autowired
    private UnitMeasureRepository unitMeasureRepository;

    private String unitMeasureNotExistMessage = "Unit Measure not exists";

    private String serviceCodeExistMessage = "Acronym code already exists";

    public void unitMeasureExistsValidation(UUID id){
        boolean exists = unitMeasureRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(unitMeasureNotExistMessage);
        }
    }

    public void acronymExistValidation(String acronym){
        boolean exists = unitMeasureRepository.existsByAcronym(acronym);
        if (exists){
            throw new ValidationException(serviceCodeExistMessage);
        }
    }

    public void acronymExistInOtherIdValidation(String acronym, UUID id){
        boolean exists = unitMeasureRepository.existsByAcronymAndIdNot(acronym, id);
        if (exists){
            throw new ValidationException(serviceCodeExistMessage);
        }
    }
}
