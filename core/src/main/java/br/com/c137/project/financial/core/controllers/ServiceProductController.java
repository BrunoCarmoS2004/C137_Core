package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ServiceProductGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ServiceProductPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ServiceProductPutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.ServiceProductService;
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
@RequestMapping("/serviceproduct")
public class ServiceProductController {
    @Autowired
    private ServiceProductService serviceProductService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<ServiceProductGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(serviceProductService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> getServiceProductById(@PathVariable UUID id) {
        ServiceProductGetDTO serviceProductGetDTO = serviceProductService.getServiceProductById(id);
        return createResponse(
                HttpStatus.OK,
                serviceProductGetDTO.id(),
                serviceProductGetDTO,
                messageUtils.getMessage("service.product.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> postServiceProduct(@Valid @RequestBody ServiceProductPostDTO serviceProductPostDTO) {
        ServiceProductGetDTO serviceProductGetDTO = serviceProductService.postServiceProduct(serviceProductPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                serviceProductGetDTO.id(),
                serviceProductGetDTO,
                messageUtils.getMessage("service.product.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> putServiceProduct(@PathVariable UUID id, @Valid @RequestBody ServiceProductPutDTO serviceProductPutDTO) {
        ServiceProductGetDTO serviceProductGetDTO = serviceProductService.putServiceProduct(id, serviceProductPutDTO);
        return createResponse(
                HttpStatus.OK,
                serviceProductGetDTO.id(),
                serviceProductGetDTO,
                messageUtils.getMessage("service.product.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceProduct(@PathVariable UUID id) {
        serviceProductService.deleteServiceProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveServiceProduct(@PathVariable UUID id) {
        serviceProductService.inactiveServiceProduct(id);
        return ResponseEntity.noContent().build();
    }
}
