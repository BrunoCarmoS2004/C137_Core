package br.com.c137.project.core.services;


import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.mappers.address.AddressAddressToGetMapper;
import br.com.c137.project.core.mappers.address.AddressPostToAddressMapper;
import br.com.c137.project.core.mappers.address.AddressPutToAddressMapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.AddressPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.AddressPutDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.Address;
import br.com.c137.project.core.multitenancy.tenant.repositorys.AddressRepository;
import br.com.c137.project.core.responses.AddressResponse;
import br.com.c137.project.core.validations.AddressValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressPostToAddressMapper addressPostToAddressMapper;

    @Autowired
    private AddressAddressToGetMapper addressAddressToGetMapper;

    @Autowired
    private AddressPutToAddressMapper addressPutToAddressMapper;

    @Autowired
    private AddressValidation addressValidation;

    @Autowired
    private ClientService clientService;

    private final String addressNotFoundMessage = "Address not found";

    private final String addressCreatedMessage = "Address Created";

    private final String addressFoundMessage = "Address Found";

    private final String addressUpdatedMessage = "Address Updated";

    public ResponseEntity<Page<AddressGetDTO>> getAll(Pageable pageable) {
        Page<AddressGetDTO> addresses = addressRepository.findBy(pageable, AddressGetDTO.class);
        return pageHasContent(addresses);
    }

    public ResponseEntity<Page<AddressGetDTO>> getAllByAddressOf(UUID id, Pageable pageable) {
        Page<AddressGetDTO> addresses = addressRepository.findAllByAddressOf(id, pageable);
        return pageHasContent(addresses);
    }

    public ResponseEntity<AddressResponse> getAddressById(UUID id) {
        AddressGetDTO addressGetDTO = addressRepository.findById(id, AddressGetDTO.class).orElseThrow(() -> new NotFoundException(addressNotFoundMessage));
        return response(HttpStatus.OK, addressGetDTO, addressFoundMessage);
    }

    public ResponseEntity<AddressResponse> postAddress(AddressPostDTO addressPostDTO) {
        //TODO, TROCAR PARA O SUPPLIER QUANDO FOR SUPPLIER O CREATED OF SUPPLIER
        clientService.clientExistsValidation(addressPostDTO.addressOf());
        Address address = addressPostToAddressMapper.mapper(addressPostDTO);
        address = addressRepository.save(address);
        clientService.updateCreationStuatus(addressPostDTO.addressOf());
        AddressGetDTO addressGetDTO = addressAddressToGetMapper.mapper(address);
        return response(HttpStatus.CREATED, addressGetDTO, addressCreatedMessage);
    }

    public ResponseEntity<AddressResponse> putAddress(UUID id, AddressPutDTO addressPostDTO) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException(addressNotFoundMessage));
        address = addressPutToAddressMapper.mapper(addressPostDTO, address);
        address = addressRepository.save(address);
        AddressGetDTO addressGetDTO = addressAddressToGetMapper.mapper(address);
        return response(HttpStatus.OK, addressGetDTO, addressUpdatedMessage);
    }

    public ResponseEntity<Void> deleteAddress(UUID id) {
        addressValidation.addressExistsValidation(id);
        addressRepository.updateEntityStatus(EntityStatus.DELETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> inactiveAddress(UUID id) {
        addressValidation.addressExistsValidation(id);
        addressRepository.updateEntityStatus(EntityStatus.INACTIVE, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ResponseEntity<Page<AddressGetDTO>> pageHasContent(Page<AddressGetDTO> addresses) {
        if (!addresses.hasContent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    private ResponseEntity<AddressResponse> response(HttpStatus httpStatus, AddressGetDTO addressGetDTO, String message) {
        return ResponseEntity.status(httpStatus).body(new AddressResponse(
                addressGetDTO.id(),
                message,
                addressGetDTO
        ));
    }
}
