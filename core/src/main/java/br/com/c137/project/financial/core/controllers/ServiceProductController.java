package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ServiceProductGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ServiceProductPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ServiceProductPutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.ServiceProductService;
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
@RequestMapping("/serviceproduct")
public class ServiceProductController {
    @Autowired
    private ServiceProductService serviceProductService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable, PagedResourcesAssembler<ServiceProductGetDTO> assembler) {
        return serviceProductService.getAll(pageable, assembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> getServiceProductById(@PathVariable UUID id) {
        return serviceProductService.getServiceProductById(id);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> postServiceProduct(@Valid @RequestBody ServiceProductPostDTO serviceProductPostDTO) {
        return serviceProductService.postServiceProduct(serviceProductPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> putServiceProduct(@PathVariable UUID id, @Valid @RequestBody ServiceProductPutDTO serviceProductPutDTO) {
        return serviceProductService.putServiceProduct(id, serviceProductPutDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceProduct(@PathVariable UUID id) {
        return serviceProductService.deleteServiceProduct(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveServiceProduct(@PathVariable UUID id) {
        return serviceProductService.inactiveServiceProduct(id);
    }
}
