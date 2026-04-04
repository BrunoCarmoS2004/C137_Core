package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.TaxRuleMapper;
import br.com.c137.project.financial.core.multitenancy.mastertenant.models.Cnae;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.TaxRuleGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.TaxRulePostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.TaxRulePutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.TaxRule;
import br.com.c137.project.financial.core.multitenancy.tenant.repositorys.TaxRuleRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.validations.TaxRuleValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.createResponse;
import static br.com.c137.project.financial.core.utils.ServiceUtils.pageHasContent;

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

    private final String taxRuleNotFoundMessage = "Tax rule not found";

    private final String taxRuleCreatedMessage = "Tax rule Created";

    private final String taxRuleFoundMessage = "Tax rule Found";

    private final String taxRuleUpdatedMessage = "Tax rule Updated";

    private final String cnaeNotFoundMessage = "CNAE not found";
    
    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<TaxRuleGetDTO> assembler) {
        Page<TaxRuleGetDTO> taxRules = taxRuleRepository.findBy(pageable, TaxRuleGetDTO.class);
        return pageHasContent(taxRules, assembler);
    }
    
    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> getTaxRuleById(UUID id) {
        TaxRuleGetDTO taxRuleGetDTO = taxRuleRepository.findById(id, TaxRuleGetDTO.class).orElseThrow(() -> new NotFoundException(taxRuleNotFoundMessage));
        return createResponse(HttpStatus.OK, id, taxRuleGetDTO, taxRuleFoundMessage);
    }

    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> postTaxRule(TaxRulePostDTO taxRulePostDTO) {
        taxRuleValidation.serviceCodeExistValidation(taxRulePostDTO.serviceCode());
        Cnae cnae = cnaeService.getCnaeByIdForRelationship(taxRulePostDTO.cnaeId());
        TaxRule taxRule = taxRuleMapper.postToTaxRule(taxRulePostDTO);
        taxRule.setCnae(cnae);
        taxRuleRepository.save(taxRule);
        TaxRuleGetDTO taxRuleGetDTO = taxRuleMapper.taxRuleToTaxRuleGetDTO(taxRule);
        return createResponse(HttpStatus.CREATED, taxRuleGetDTO.id(), taxRuleGetDTO, taxRuleCreatedMessage);
    }

    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> putTaxRule(UUID id,TaxRulePutDTO taxRulePutDTO) {
        taxRuleValidation.serviceCodeExistInOtherIdValidation(taxRulePutDTO.serviceCode(), id);
        TaxRule taxRule = taxRuleRepository.findById(id).orElseThrow(() -> new NotFoundException(taxRuleNotFoundMessage));
        if (!Objects.equals(taxRulePutDTO.cnaeId(), taxRule.getCnae().getId())) {
            Cnae cnae = cnaeService.getCnaeByIdForRelationship(taxRulePutDTO.cnaeId());
            taxRule.setCnae(cnae);
        }
        taxRule = taxRuleMapper.putToTaxRule(taxRulePutDTO, taxRule);
        taxRuleRepository.save(taxRule);
        TaxRuleGetDTO taxRuleGetDTO = taxRuleMapper.taxRuleToTaxRuleGetDTO(taxRule);
        return createResponse(HttpStatus.OK, taxRuleGetDTO.id(), taxRuleGetDTO, taxRuleUpdatedMessage);
    }

    public ResponseEntity<Void> deleteTaxRule(UUID id) {
        taxRuleExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> inactiveTaxRule(UUID id) {
        taxRuleExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected void taxRuleExistsValidation(UUID id) {
        taxRuleValidation.taxRuleExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        taxRuleRepository.updateEntityStatus(entityStatus, id);
    }

    protected TaxRule getTaxRuleByIdForRelationship(UUID id) {
        return taxRuleRepository.findById(id).orElseThrow(() -> new NotFoundException(taxRuleNotFoundMessage));
    }
}
