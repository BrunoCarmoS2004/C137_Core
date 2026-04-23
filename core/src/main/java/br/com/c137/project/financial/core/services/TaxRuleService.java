package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.TaxRuleMapper;
import br.com.c137.project.financial.core.multitenancy.mastertenant.models.Cnae;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.TaxRuleGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.TaxRulePostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.TaxRulePutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.serviceproduct.TaxRule;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.TaxRuleRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.utils.MessageUtils;
import br.com.c137.project.financial.core.validations.TaxRuleValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.createResponse;

@Service
public class TaxRuleService {
    @Autowired
    private TaxRuleRepository taxRuleRepository;

    @Autowired
    private CnaeService cnaeService;

    @Autowired
    private TaxRuleMapper taxRuleMapper;

    @Autowired
    private TaxRuleValidation taxRuleValidation;

    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "taxrules", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<TaxRuleGetDTO> getAll(Pageable pageable) {
        Page<TaxRuleGetDTO> taxRules = taxRuleRepository.findBy(pageable, TaxRuleGetDTO.class);
        return new PagedModel<>(taxRules);
    }
    @Cacheable(value = "taxrule", key = "#id")
    public TaxRuleGetDTO getTaxRuleById(UUID id) {
        return taxRuleRepository.findById(id, TaxRuleGetDTO.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }
    @CacheEvict(value = "taxrules", allEntries = true)
    public TaxRuleGetDTO postTaxRule(TaxRulePostDTO taxRulePostDTO) {
        taxRuleValidation.serviceCodeExistValidation(taxRulePostDTO.serviceCode());
        Cnae cnae = cnaeService.getCnaeById(taxRulePostDTO.cnaeId());
        TaxRule taxRule = taxRuleMapper.postToTaxRule(taxRulePostDTO);
        taxRule.setCnae(cnae);
        taxRuleRepository.save(taxRule);
        return taxRuleMapper.taxRuleToTaxRuleGetDTO(taxRule);
    }

    @Caching(evict = {
            @CacheEvict(value = "taxrules", allEntries = true),
            @CacheEvict(value = "taxrule", key = "#id")
    })
    public TaxRuleGetDTO putTaxRule(UUID id, TaxRulePutDTO taxRulePutDTO) {
        taxRuleValidation.serviceCodeExistInOtherIdValidation(taxRulePutDTO.serviceCode(), id);
        TaxRule taxRule = taxRuleRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        if (!Objects.equals(taxRulePutDTO.cnaeId(), taxRule.getCnae().getId())) {
            Cnae cnae = cnaeService.getCnaeById(taxRulePutDTO.cnaeId());
            taxRule.setCnae(cnae);
        }
        taxRule = taxRuleMapper.putToTaxRule(taxRulePutDTO, taxRule);
        taxRuleRepository.save(taxRule);
        return taxRuleMapper.taxRuleToTaxRuleGetDTO(taxRule);
    }

    @Caching(evict = {
            @CacheEvict(value = "taxRules", allEntries = true),
            @CacheEvict(value = "taxRule", key = "#id")
    })
    public void deleteTaxRule(UUID id) {
        taxRuleExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "taxRules", allEntries = true),
            @CacheEvict(value = "taxRule", key = "#id")
    })
    public void inactiveTaxRule(UUID id) {
        taxRuleExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void taxRuleExistsValidation(UUID id) {
        taxRuleValidation.taxRuleExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        taxRuleRepository.updateEntityStatus(entityStatus, id);
    }

    protected TaxRule getTaxRuleByIdForRelationship(UUID id) {
        return taxRuleRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }
    
    private String getNotFoundMessage(){
        return messageUtils.getMessage("tax.rule.not-found");
    }
}
