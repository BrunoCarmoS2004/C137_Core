package br.com.c137.project.core.services;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.multitenancy.mastertenant.models.Cnae;
import br.com.c137.project.core.multitenancy.mastertenant.repositorys.CnaeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CnaeService {
    @Autowired
    private CnaeRepository cnaeRepository;

    private final String cnaeNotFoundMessage = "Cnae not found";

    protected Cnae getCnaeByIdForRelationship(Long id) {
        return cnaeRepository.findById(id).orElseThrow(() -> new NotFoundException(cnaeNotFoundMessage));
    }
}
