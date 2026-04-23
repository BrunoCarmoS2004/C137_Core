package br.com.c137.project.financial.core.validations;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.exceptions.ValidationException;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.UnitMeasureRepository;
import br.com.c137.project.financial.core.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UnitMeasureValidation {
    @Autowired
    private UnitMeasureRepository unitMeasureRepository;

    @Autowired
    private MessageUtils messageUtils;

    public void unitMeasureExistsValidation(UUID id){
        boolean exists = unitMeasureRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException(messageUtils.getMessage("unit.measure.not-exists"));
        }
    }

    public void acronymExistValidation(String acronym){
        boolean exists = unitMeasureRepository.existsByAcronym(acronym);
        if (exists){
            throw new ValidationException(getserviceCodeExistMessage());
        }
    }

    public void acronymExistInOtherIdValidation(String acronym, UUID id){
        boolean exists = unitMeasureRepository.existsByAcronymAndIdNot(acronym, id);
        if (exists){
            throw new ValidationException(getserviceCodeExistMessage());
        }
    }

    private String getserviceCodeExistMessage(){
        return messageUtils.getMessage("unit.measure.acronym.exists");
    }
}
