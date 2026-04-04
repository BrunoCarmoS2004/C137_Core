package br.com.c137.project.core.controllers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.AddressPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.AddressPutDTO;
import br.com.c137.project.core.responses.ResponsePayload;
import br.com.c137.project.core.services.AddressService;
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
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable, PagedResourcesAssembler<AddressGetDTO> assembler) {
        return addressService.getAll(pageable, assembler);
    }

    @GetMapping("/addressof/{id}")
    public ResponseEntity<?> getAllByAddressOfId(@PathVariable UUID id, @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable, PagedResourcesAssembler<AddressGetDTO> assembler) {
        return addressService.getAllByAddressOf(id, pageable, assembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<AddressGetDTO>> getAddressById(@PathVariable UUID id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<AddressGetDTO>> postAddress(@Valid @RequestBody AddressPostDTO addressPostDTO){
        return addressService.postAddress(addressPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<AddressGetDTO>> putAddress(@PathVariable UUID id, @Valid @RequestBody AddressPutDTO addressPutDTO){
        return addressService.putAddress(id, addressPutDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id){
        return addressService.deleteAddress(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveAddress(@PathVariable UUID id){
        return addressService.inactiveAddress(id);
    }
}
