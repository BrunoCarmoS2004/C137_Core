package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.TaxRuleGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.TaxRulePostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.TaxRulePutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.TaxRuleService;
import br.com.c137.project.financial.core.utils.MessageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.createResponse;

@RestController
@RequestMapping("/taxrule")
public class TaxRuleController {
    @Autowired
    private TaxRuleService taxRuleService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<TaxRuleGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(taxRuleService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> getTaxRuleById(@PathVariable UUID id) {
        TaxRuleGetDTO taxRuleGetDTO = taxRuleService.getTaxRuleById(id);
        return createResponse(
                HttpStatus.OK,
                taxRuleGetDTO.id(),
                taxRuleGetDTO,
                messageUtils.getMessage("tax.rule.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> postTaxRule(@Valid @RequestBody TaxRulePostDTO taxRulePostDTO) {
        TaxRuleGetDTO taxRuleGetDTO = taxRuleService.postTaxRule(taxRulePostDTO);
        return createResponse(
                HttpStatus.CREATED,
                taxRuleGetDTO.id(),
                taxRuleGetDTO,
                messageUtils.getMessage("tax.rule.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> putTaxRule(@PathVariable UUID id, @Valid @RequestBody TaxRulePutDTO taxRulePutDTO) {
        TaxRuleGetDTO taxRuleGetDTO = taxRuleService.putTaxRule(id, taxRulePutDTO);
        return createResponse(
                HttpStatus.OK,
                taxRuleGetDTO.id(),
                taxRuleGetDTO,
                messageUtils.getMessage("tax.rule.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxRule(@PathVariable UUID id) {
        taxRuleService.deleteTaxRule(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveTaxRule(@PathVariable UUID id) {
        taxRuleService.inactiveTaxRule(id);
        return ResponseEntity.noContent().build();
    }
}
