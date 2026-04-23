package br.com.c137.project.financial.core.services;


import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.AddressMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.AddressPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.AddressPutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.Address;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.AddressRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.utils.MessageUtils;
import br.com.c137.project.financial.core.validations.AddressValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.createResponse;

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

    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "addresses", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<AddressGetDTO> getAll(Pageable pageable) {
        Page<AddressGetDTO> addresses = addressRepository.findBy(pageable, AddressGetDTO.class);
        return new PagedModel<>(addresses);
    }

    @Cacheable(
            value = "addressesof",
            key = "#id.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
    )
    public PagedModel<AddressGetDTO> getAllByAddressOf(UUID id, Pageable pageable) {
        Page<AddressGetDTO> addresses = addressRepository.findAllByAddressOf(id, pageable);
        return new PagedModel<>(addresses);
    }

    @Cacheable(value = "address", key = "#id")
    public AddressGetDTO getAddressById(UUID id) {
        return addressRepository.findById(id, AddressGetDTO.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "addresses", allEntries = true)
    public AddressGetDTO postAddress(AddressPostDTO addressPostDTO) {
        addressValidation.zipCodeAndNumberExistsValidation(addressPostDTO.zipCode(), addressPostDTO.number());
        existsCreatedForEntity(addressPostDTO);
        Address address = addressMapper.postToAddress(addressPostDTO);
        address = addressRepository.save(address);
        updateCreatedForEntityStatus(addressPostDTO);
        return addressMapper.addressToAddressGetDTO(address);
    }

    @Caching(evict = {
            @CacheEvict(value = "addresses", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(
                    value = "addressesof",
                    key = "#id.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
            ),
            @CacheEvict(value = "address", key = "#id")
    })
    public AddressGetDTO putAddress(UUID id, AddressPutDTO addressPutDTO) {
        addressValidation.zipCodeAndNumberInOtherIdExistsValidation(addressPutDTO.zipCode(), addressPutDTO.number(), id);
        Address address = addressRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        address = addressMapper.putToAddress(addressPutDTO, address);
        address = addressRepository.save(address);
        return addressMapper.addressToAddressGetDTO(address);
    }

    @Caching(evict = {
            @CacheEvict(value = "addresses", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(
                    value = "addressesof",
                    key = "#id.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
            ),
            @CacheEvict(value = "address", key = "#id")
    })
    public void deleteAddress(UUID id) {
        addressValidation.addressExistsValidation(id);
        addressRepository.updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "addresses", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(
                    value = "addressesof",
                    key = "#id.toString() + '-' + #pageable.pageNumber + '-' + #pageable.pageSize"
            ),
            @CacheEvict(value = "address", key = "#id")
    })
    public void inactiveAddress(UUID id) {
        addressValidation.addressExistsValidation(id);
        addressRepository.updateEntityStatus(EntityStatus.INACTIVE, id);
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

    private String getNotFoundMessage(){
        return messageUtils.getMessage("address.not-found");
    }
}
