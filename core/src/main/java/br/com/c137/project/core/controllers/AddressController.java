package br.com.c137.project.core.controllers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.AddressPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.AddressPutDTO;
import br.com.c137.project.core.responses.AddressResponse;
import br.com.c137.project.core.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<Page<AddressGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return addressService.getAll(pageable);
    }

    @GetMapping("/addressOf/{id}")
    public ResponseEntity<Page<AddressGetDTO>> getAllByAddressOfId(@PathVariable UUID id, @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return addressService.getAllByAddressOf(id, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(@PathVariable UUID id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    public ResponseEntity<AddressResponse> postAddress(@Valid @RequestBody AddressPostDTO addressPostDTO){
        return addressService.postAddress(addressPostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> putAddress(@PathVariable UUID id, @Valid @RequestBody AddressPutDTO addressPutDTO){
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
