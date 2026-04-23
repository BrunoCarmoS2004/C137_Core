package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.SupplierMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.SupplierGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.SupplierPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.SupplierPutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.partner.Supplier;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.SupplierRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.utils.MessageUtils;
import br.com.c137.project.financial.core.validations.SupplierValidation;
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
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierValidation supplierValidation;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private MessageUtils messageUtils;


    @Cacheable(value = "suppliers", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<SupplierGetDTO> getAll(Pageable pageable) {
        Page<SupplierGetDTO> suppliers = supplierRepository.findBy(pageable, SupplierGetDTO.class);
        return new PagedModel<>(suppliers);
    }

    @Cacheable(value = "supplier", key = "#id")
    public SupplierGetDTO getSupplierById(UUID id) {
        return supplierRepository.findById(id, SupplierGetDTO.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "suppliers", allEntries = true)
    public SupplierGetDTO postSupplier(SupplierPostDTO supplierPostDTO) {
        supplierValidation.inscriptionExistsValidation(supplierPostDTO.inscription());
        supplierValidation.emailExistsValidation(supplierPostDTO.email());
        Supplier supplier = supplierMapper.postToSupplier(supplierPostDTO);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.supplierToSupplierGetDTO(supplier);
    }

    @Caching(evict = {
            @CacheEvict(value = "suppliers", allEntries = true),
            @CacheEvict(value = "supplier", key = "#id")
    })
    public SupplierGetDTO putSupplier(UUID id, SupplierPutDTO supplierPutDTO) {
        supplierValidation.inscriptionExistsInOtherIdValidation(supplierPutDTO.inscription(), id);
        supplierValidation.emailExistsInOtherIdValidation(supplierPutDTO.email(), id);
        Supplier supplier = supplierRepository.findById(id, Supplier.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        supplier = supplierMapper.putToSupplier(supplierPutDTO, supplier);
        supplier = supplierRepository.save(supplier);
        return supplierMapper.supplierToSupplierGetDTO(supplier);
    }

    @Caching(evict = {
            @CacheEvict(value = "suppliers", allEntries = true),
            @CacheEvict(value = "supplier", key = "#id")
    })
    public void deleteSupplier(UUID id) {
        supplierExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "suppliers", allEntries = true),
            @CacheEvict(value = "supplier", key = "#id")
    })
    public void inactiveSupplier(UUID id) {
        supplierExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void updateCreationStuatus(UUID id) {
        supplierRepository.updateCreationStatus(CreationStatus.COMPLETED, id);
    }

    protected void supplierExistsValidation(UUID id) {
        supplierValidation.supplierExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        supplierRepository.updateEntityStatus(entityStatus, id);
    }

    private String getNotFoundMessage() {
        return messageUtils.getMessage("supplier.not-found");
    }
}

