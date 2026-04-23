package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.multitenancy.mastertenant.models.Cnae;
import br.com.c137.project.financial.core.multitenancy.mastertenant.repositories.CnaeRepository;
import br.com.c137.project.financial.core.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CnaeService {
    @Autowired
    private CnaeRepository cnaeRepository;

    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "cnae", key = "#id")
    public Cnae getCnaeById(Long id) {
        return cnaeRepository.findById(id).orElseThrow(() -> new NotFoundException(messageUtils.getMessage("cnae.not-found")));
    }
}
