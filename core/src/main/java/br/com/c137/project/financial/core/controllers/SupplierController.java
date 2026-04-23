package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.SupplierGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.SupplierPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.SupplierPutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.SupplierService;
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
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<SupplierGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(supplierService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<SupplierGetDTO>> getSupplierById(@PathVariable UUID id) {
        SupplierGetDTO supplierGetDTO = supplierService.getSupplierById(id);
        return createResponse(
                HttpStatus.OK,
                supplierGetDTO.id(),
                supplierGetDTO,
                messageUtils.getMessage("supplier.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<SupplierGetDTO>> postSupplier(@Valid @RequestBody SupplierPostDTO supplierPostDTO) {
        SupplierGetDTO supplierGetDTO = supplierService.postSupplier(supplierPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                supplierGetDTO.id(),
                supplierGetDTO,
                messageUtils.getMessage("supplier.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<SupplierGetDTO>> putSupplier(@PathVariable UUID id, @Valid @RequestBody SupplierPutDTO supplierPutDTO) {
        SupplierGetDTO supplierGetDTO = supplierService.putSupplier(id, supplierPutDTO);
        return createResponse(
                HttpStatus.OK,
                supplierGetDTO.id(),
                supplierGetDTO,
                messageUtils.getMessage("supplier.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveSupplier(@PathVariable UUID id) {
        supplierService.inactiveSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
