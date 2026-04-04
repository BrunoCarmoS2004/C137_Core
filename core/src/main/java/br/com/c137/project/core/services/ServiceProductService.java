package br.com.c137.project.core.services;

import br.com.c137.project.core.exceptions.NotFoundException;
import br.com.c137.project.core.mappers.ServiceProductMapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ServiceProductGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.ServiceProductPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ServiceProductPutDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.ServiceProduct;
import br.com.c137.project.core.multitenancy.tenant.models.TaxRule;
import br.com.c137.project.core.multitenancy.tenant.models.UnitMeasure;
import br.com.c137.project.core.multitenancy.tenant.repositorys.ServiceProductRepository;
import br.com.c137.project.core.responses.ResponsePayload;
import br.com.c137.project.core.validations.ServiceProductValidation;
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
public class ServiceProductService {
    @Autowired
    private ServiceProductRepository serviceProductRepository;

    @Autowired
    private ServiceProductValidation serviceProductValidation;

    @Autowired
    private TaxRuleService taxRuleService;

    @Autowired
    private UnitMeasureService unitMeasureService;

    @Autowired
    private ServiceProductMapper serviceProductMapper;

    private final String serviceProductNotFoundMessage = "Service/Product not found";

    private final String serviceProductCreatedMessage = "Service/Product Created";

    private final String serviceProductFoundMessage = "Service/Product Found";

    private final String serviceProductUpdatedMessage = "Service/Product Updated";

    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<ServiceProductGetDTO> assembler) {
        Page<ServiceProductGetDTO> serviceProducts = serviceProductRepository.findBy(pageable, ServiceProductGetDTO.class);
        return pageHasContent(serviceProducts, assembler);
    }

    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> getServiceProductById(UUID id) {
        ServiceProductGetDTO serviceProductGetDTO = serviceProductRepository.findById(id, ServiceProductGetDTO.class).orElseThrow(() -> new NotFoundException(serviceProductNotFoundMessage));
        return createResponse(HttpStatus.OK, serviceProductGetDTO.id(), serviceProductGetDTO, serviceProductFoundMessage);
    }

    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> postServiceProduct(ServiceProductPostDTO serviceProductPostDTO) {
        serviceProductValidation.codeExistsValidation(serviceProductPostDTO.code());
        TaxRule taxRule = taxRuleService.getTaxRuleByIdForRelationship(serviceProductPostDTO.taxRuleId());
        UnitMeasure unitMeasure = unitMeasureService.getUnitMeasureByIdForRelationship(serviceProductPostDTO.unitMeasureId());
        ServiceProduct serviceProduct = serviceProductMapper.postToServiceProduct(serviceProductPostDTO);
        serviceProduct.setTaxRule(taxRule);
        serviceProduct.setUnitMeasure(unitMeasure);
        serviceProduct = serviceProductRepository.save(serviceProduct);
        ServiceProductGetDTO serviceProductGetDTO = serviceProductMapper.serviceProductToServiceProductGetDTO(serviceProduct);
        return createResponse(HttpStatus.CREATED, serviceProductGetDTO.id(), serviceProductGetDTO, serviceProductCreatedMessage);
    }

    public ResponseEntity<ResponsePayload<ServiceProductGetDTO>> putServiceProduct(UUID id, ServiceProductPutDTO serviceProductPutDTO) {
        serviceProductValidation.codeExistsInOtherIdValidation(serviceProductPutDTO.code(), id);
        ServiceProduct serviceProduct = serviceProductRepository.findById(id, ServiceProduct.class).orElseThrow(() -> new NotFoundException(serviceProductNotFoundMessage));
        serviceProduct = serviceProductMapper.putToServiceProduct(serviceProductPutDTO, serviceProduct);
        serviceProduct = serviceProductRepository.save(serviceProduct);
        ServiceProductGetDTO serviceProductGetDTO = serviceProductMapper.serviceProductToServiceProductGetDTO(serviceProduct);
        return createResponse(HttpStatus.OK, serviceProductGetDTO.id(), serviceProductGetDTO, serviceProductUpdatedMessage);
    }

    public ResponseEntity<Void> deleteServiceProduct(UUID id) {
        serviceProductExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> inactiveServiceProduct(UUID id) {
        serviceProductExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected void serviceProductExistsValidation(UUID id) {
        serviceProductValidation.serviceProductExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        serviceProductRepository.updateEntityStatus(entityStatus, id);
    }
}
