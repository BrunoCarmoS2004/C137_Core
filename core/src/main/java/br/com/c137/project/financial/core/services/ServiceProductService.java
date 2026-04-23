package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.ServiceProductMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ServiceProductGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ServiceProductPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ServiceProductPutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.serviceproduct.ServiceProduct;
import br.com.c137.project.financial.core.multitenancy.tenant.models.serviceproduct.TaxRule;
import br.com.c137.project.financial.core.multitenancy.tenant.models.serviceproduct.UnitMeasure;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.ServiceProductRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.utils.MessageUtils;
import br.com.c137.project.financial.core.validations.ServiceProductValidation;
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

    @Autowired
    private MessageUtils messageUtils;



    @Cacheable(value = "serviceproducts", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<ServiceProductGetDTO> getAll(Pageable pageable) {
        Page<ServiceProductGetDTO> serviceProducts = serviceProductRepository.findBy(pageable, ServiceProductGetDTO.class);
        return new PagedModel<>(serviceProducts);
    }

    @Cacheable(value = "serviceproduct", key = "#id")
    public ServiceProductGetDTO getServiceProductById(UUID id) {
        return serviceProductRepository.findById(id, ServiceProductGetDTO.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "serviceproducts", allEntries = true)
    public ServiceProductGetDTO postServiceProduct(ServiceProductPostDTO serviceProductPostDTO) {
        serviceProductValidation.codeExistsValidation(serviceProductPostDTO.code());
        TaxRule taxRule = taxRuleService.getTaxRuleByIdForRelationship(serviceProductPostDTO.taxRuleId());
        UnitMeasure unitMeasure = unitMeasureService.getUnitMeasureByIdForRelationship(serviceProductPostDTO.unitMeasureId());
        ServiceProduct serviceProduct = serviceProductMapper.postToServiceProduct(serviceProductPostDTO);
        serviceProduct.setTaxRule(taxRule);
        serviceProduct.setUnitMeasure(unitMeasure);
        serviceProduct = serviceProductRepository.save(serviceProduct);
        return serviceProductMapper.serviceProductToServiceProductGetDTO(serviceProduct);
    }

    @Caching(evict = {
            @CacheEvict(value = "serviceproducts", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(value = "serviceproduct", key = "#id")
    })
    public ServiceProductGetDTO putServiceProduct(UUID id, ServiceProductPutDTO serviceProductPutDTO) {
        serviceProductValidation.codeExistsInOtherIdValidation(serviceProductPutDTO.code(), id);
        TaxRule taxRule = taxRuleService.getTaxRuleByIdForRelationship(serviceProductPutDTO.taxRuleId());
        UnitMeasure unitMeasure = unitMeasureService.getUnitMeasureByIdForRelationship(serviceProductPutDTO.unitMeasureId());
        ServiceProduct serviceProduct = serviceProductRepository.findById(id, ServiceProduct.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        serviceProduct = serviceProductMapper.putToServiceProduct(serviceProductPutDTO, serviceProduct);
        serviceProduct.setTaxRule(taxRule);
        serviceProduct.setUnitMeasure(unitMeasure);
        serviceProduct = serviceProductRepository.save(serviceProduct);
        return serviceProductMapper.serviceProductToServiceProductGetDTO(serviceProduct);
    }

    @Caching(evict = {
            @CacheEvict(value = "serviceproducts", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(value = "serviceproduct", key = "#id")
    })
    public void deleteServiceProduct(UUID id) {
        serviceProductExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "serviceproducts", key = "#pageable.pageNumber + '-' + #pageable.pageSize"),
            @CacheEvict(value = "serviceproduct", key = "#id")
    })
    public void inactiveServiceProduct(UUID id) {
        serviceProductExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void serviceProductExistsValidation(UUID id) {
        serviceProductValidation.serviceProductExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        serviceProductRepository.updateEntityStatus(entityStatus, id);
    }

    private String getNotFoundMessage(){
        return messageUtils.getMessage("service.product.not-found");
    }
}
