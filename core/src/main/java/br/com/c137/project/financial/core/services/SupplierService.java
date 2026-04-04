package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.SupplierMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.SupplierGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.SupplierPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.SupplierPutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.partner.Supplier;
import br.com.c137.project.financial.core.multitenancy.tenant.repositorys.SupplierRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.validations.SupplierValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.createResponse;
import static br.com.c137.project.financial.core.utils.ServiceUtils.pageHasContent;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private SupplierValidation supplierValidation;

    @Autowired
    private SupplierMapper supplierMapper;

    private final String supplierNotFoundMessage = "Supplier not found";

    private final String supplierCreatedMessage = "Supplier Created";

    private final String supplierFoundMessage = "Supplier Found";

    private final String supplierUpdatedMessage = "Supplier Updated";

    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<SupplierGetDTO> assembler) {
        Page<SupplierGetDTO> suppliers = supplierRepository.findBy(pageable, SupplierGetDTO.class);
        return pageHasContent(suppliers, assembler);
    }

    public ResponseEntity<ResponsePayload<SupplierGetDTO>> getSupplierById(UUID id) {
        SupplierGetDTO supplierGetDTO = supplierRepository.findById(id, SupplierGetDTO.class).orElseThrow(() -> new NotFoundException(supplierNotFoundMessage));
        return createResponse(HttpStatus.OK, supplierGetDTO.id(), supplierGetDTO, supplierFoundMessage);
    }

    public ResponseEntity<ResponsePayload<SupplierGetDTO>> postSupplier(SupplierPostDTO supplierPostDTO) {
        supplierValidation.inscriptionExistsValidation(supplierPostDTO.inscription());
        supplierValidation.emailExistsValidation(supplierPostDTO.email());
        Supplier supplier = supplierMapper.postToSupplier(supplierPostDTO);
        supplier = supplierRepository.save(supplier);
        SupplierGetDTO supplierGetDTO = supplierMapper.supplierToSupplierGetDTO(supplier);
        return createResponse(HttpStatus.CREATED, supplierGetDTO.id(), supplierGetDTO, supplierCreatedMessage);
    }

    public ResponseEntity<ResponsePayload<SupplierGetDTO>> putSupplier(UUID id, SupplierPutDTO supplierPutDTO) {
        supplierValidation.inscriptionExistsInOtherIdValidation(supplierPutDTO.inscription(), id);
        supplierValidation.emailExistsInOtherIdValidation(supplierPutDTO.email(), id);
        Supplier supplier = supplierRepository.findById(id, Supplier.class).orElseThrow(() -> new NotFoundException(supplierNotFoundMessage));
        supplier = supplierMapper.putToSupplier(supplierPutDTO, supplier);
        supplier = supplierRepository.save(supplier);
        SupplierGetDTO supplierGetDTO = supplierMapper.supplierToSupplierGetDTO(supplier);
        return createResponse(HttpStatus.OK, supplierGetDTO.id(), supplierGetDTO, supplierUpdatedMessage);
    }

    public ResponseEntity<Void> deleteSupplier(UUID id) {
        supplierExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> inactiveSupplier(UUID id) {
        supplierExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
}
