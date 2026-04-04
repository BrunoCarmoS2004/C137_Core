package br.com.c137.project.core.controllers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.SupplierGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.SupplierPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.SupplierPutDTO;
import br.com.c137.project.core.responses.ResponsePayload;
import br.com.c137.project.core.services.SupplierService;
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
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable, PagedResourcesAssembler<SupplierGetDTO> assembler) {
        return supplierService.getAll(pageable, assembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<SupplierGetDTO>> getSupplierById(@PathVariable UUID id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<SupplierGetDTO>> postSupplier(@Valid @RequestBody SupplierPostDTO supplierPostDTO) {
        return supplierService.postSupplier(supplierPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<SupplierGetDTO>> putSupplier(@PathVariable UUID id, @Valid @RequestBody SupplierPutDTO supplierPutDTO) {
        return supplierService.putSupplier(id, supplierPutDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID id) {
        return supplierService.deleteSupplier(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveSupplier(@PathVariable UUID id) {
        return supplierService.inactiveSupplier(id);
    }
}
