package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.TaxRuleGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.TaxRulePostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.TaxRulePutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.TaxRuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/taxrule")
public class TaxRuleController {
    @Autowired
    private TaxRuleService taxRuleService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable, PagedResourcesAssembler<TaxRuleGetDTO> assembler) {
        return taxRuleService.getAll(pageable, assembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> getTaxRuleById(@PathVariable UUID id) {
        return taxRuleService.getTaxRuleById(id);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> postTaxRule(@Valid @RequestBody TaxRulePostDTO taxRulePostDTO) {
        return taxRuleService.postTaxRule(taxRulePostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<TaxRuleGetDTO>> putTaxRule(@PathVariable UUID id, @Valid @RequestBody TaxRulePutDTO taxRulePutDTO) {
        return taxRuleService.putTaxRule(id, taxRulePutDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxRule(@PathVariable UUID id) {
        return taxRuleService.deleteTaxRule(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveTaxRule(@PathVariable UUID id) {
        return taxRuleService.inactiveTaxRule(id);
    }
}
