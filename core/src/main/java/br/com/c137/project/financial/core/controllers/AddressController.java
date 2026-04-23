package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.AddressPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.AddressPutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.AddressService;
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
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<AddressGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAll(pageable));
    }

    @GetMapping("/addressof/{id}")
    public ResponseEntity<PagedModel<AddressGetDTO>> getAllByAddressOfId(@PathVariable UUID id, @PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAllByAddressOf(id, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<AddressGetDTO>> getAddressById(@PathVariable UUID id) {
        AddressGetDTO addressGetDTO = addressService.getAddressById(id);
        return createResponse(
                HttpStatus.OK,
                addressGetDTO.id(),
                addressGetDTO,
                messageUtils.getMessage("address.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<AddressGetDTO>> postAddress(@Valid @RequestBody AddressPostDTO addressPostDTO) {
        AddressGetDTO addressGetDTO = addressService.postAddress(addressPostDTO);
        return createResponse(
                HttpStatus.CREATED,
                addressGetDTO.id(),
                addressGetDTO,
                messageUtils.getMessage("address.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<AddressGetDTO>> putAddress(@PathVariable UUID id, @Valid @RequestBody AddressPutDTO addressPutDTO) {
        AddressGetDTO addressGetDTO = addressService.putAddress(id, addressPutDTO);
        return createResponse(
                HttpStatus.OK,
                addressGetDTO.id(),
                addressGetDTO,
                messageUtils.getMessage("address.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveAddress(@PathVariable UUID id) {
        addressService.inactiveAddress(id);
        return ResponseEntity.noContent().build();
    }
}
