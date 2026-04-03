package br.com.c137.project.core.services;


import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.mappers.AddressMapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.AddressPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.AddressPutDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.Address;
import br.com.c137.project.core.multitenancy.tenant.repositorys.AddressRepository;
import br.com.c137.project.core.responses.ResponsePayload;
import br.com.c137.project.core.validations.AddressValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.c137.project.core.utils.ServiceUtils.createResponse;
import static br.com.c137.project.core.utils.ServiceUtils.pageHasContent;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressValidation addressValidation;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SupplierService supplierService;

    private final String addressNotFoundMessage = "Address not found";

    private final String addressCreatedMessage = "Address Created";

    private final String addressFoundMessage = "Address Found";

    private final String addressUpdatedMessage = "Address Updated";

    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<AddressGetDTO> assembler) {
        Page<AddressGetDTO> addresses = addressRepository.findBy(pageable, AddressGetDTO.class);
        return pageHasContent(addresses, assembler);
    }

    public ResponseEntity<?> getAllByAddressOf(UUID id, Pageable pageable, PagedResourcesAssembler<AddressGetDTO> assembler) {
        Page<AddressGetDTO> addresses = addressRepository.findAllByAddressOf(id, pageable);
        return pageHasContent(addresses, assembler);
    }

    public ResponseEntity<ResponsePayload<AddressGetDTO>> getAddressById(UUID id) {
        AddressGetDTO addressGetDTO = addressRepository.findById(id, AddressGetDTO.class).orElseThrow(() -> new NotFoundException(addressNotFoundMessage));
        return createResponse(HttpStatus.OK, addressGetDTO.id(), addressGetDTO, addressFoundMessage);
    }

    public ResponseEntity<ResponsePayload<AddressGetDTO>> postAddress(AddressPostDTO addressPostDTO) {
        existsCreatedForEntity(addressPostDTO);
        Address address = addressMapper.postToAddress(addressPostDTO);
        address = addressRepository.save(address);
        updateCreatedForEntityStatus(addressPostDTO);
        AddressGetDTO addressGetDTO = addressMapper.addressToAddressGetDTO(address);
        return createResponse(HttpStatus.CREATED, addressGetDTO.id(), addressGetDTO, addressCreatedMessage);
    }

    public ResponseEntity<ResponsePayload<AddressGetDTO>> putAddress(UUID id, AddressPutDTO addressPostDTO) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException(addressNotFoundMessage));
        address = addressMapper.putToAddress(addressPostDTO, address);
        address = addressRepository.save(address);
        AddressGetDTO addressGetDTO = addressMapper.addressToAddressGetDTO(address);
        return createResponse(HttpStatus.OK, addressGetDTO.id(), addressGetDTO, addressUpdatedMessage);
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

    private void updateCreatedForEntityStatus(AddressPostDTO addressPostDTO) {
        if (addressPostDTO.createdFor() == CreatedFor.CLIENT){
            clientService.updateCreationStuatus(addressPostDTO.addressOf());
        } else if (addressPostDTO.createdFor() == CreatedFor.SUPPLIER) {
            supplierService.updateCreationStuatus(addressPostDTO.addressOf());
        }
    }

    private void existsCreatedForEntity(AddressPostDTO addressPostDTO) {
        if (addressPostDTO.createdFor() == CreatedFor.CLIENT){
            clientService.clientExistsValidation(addressPostDTO.addressOf());
        } else if (addressPostDTO.createdFor() == CreatedFor.SUPPLIER) {
            supplierService.supplierExistsValidation(addressPostDTO.addressOf());
        }
    }
}
