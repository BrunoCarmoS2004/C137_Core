package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.UnitMeasureMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.UnitMeasureGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.UnitMeasurePostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.UnitMeasurePutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.serviceproduct.UnitMeasure;
import br.com.c137.project.financial.core.multitenancy.tenant.repositories.UnitMeasureRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.utils.MessageUtils;
import br.com.c137.project.financial.core.validations.UnitMeasureValidation;
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
public class UnitMeasureService {
    @Autowired
    private UnitMeasureRepository unitMeasureRepository;

    @Autowired
    private UnitMeasureMapper unitMeasureMapper;

    @Autowired
    private UnitMeasureValidation unitMeasureValidation;

    @Autowired
    private MessageUtils messageUtils;

    @Cacheable(value = "unitmeasures", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public PagedModel<UnitMeasureGetDTO> getAll(Pageable pageable) {
        Page<UnitMeasureGetDTO> unitMeasures = unitMeasureRepository.findBy(pageable, UnitMeasureGetDTO.class);
        return new PagedModel<>(unitMeasures);
    }

    @Cacheable(value = "unitmeasure", key = "#id")
    public UnitMeasureGetDTO getUnitMeasureById(UUID id) {
        return unitMeasureRepository.findById(id, UnitMeasureGetDTO.class).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }

    @CacheEvict(value = "unitmeasures", allEntries = true)
    public UnitMeasureGetDTO postUnitMeasure(UnitMeasurePostDTO unitMeasurePostDTO) {
        unitMeasureValidation.acronymExistValidation(unitMeasurePostDTO.acronym());
        UnitMeasure unitMeasure = unitMeasureMapper.postToUnitMeasure(unitMeasurePostDTO);
        unitMeasureRepository.save(unitMeasure);
        return unitMeasureMapper.unitMeasureToUnitMeasureGetDTO(unitMeasure);
    }

    @Caching(evict = {
            @CacheEvict(value = "unitmeasures", allEntries = true),
            @CacheEvict(value = "unitmeasure", key = "#id")
    })
    public UnitMeasureGetDTO putUnitMeasure(UUID id, UnitMeasurePutDTO unitMeasurePutDTO) {
        unitMeasureValidation.acronymExistInOtherIdValidation(unitMeasurePutDTO.acronym(), id);
        UnitMeasure unitMeasure = unitMeasureRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
        unitMeasure = unitMeasureMapper.putToUnitMeasure(unitMeasurePutDTO, unitMeasure);
        unitMeasureRepository.save(unitMeasure);
        return unitMeasureMapper.unitMeasureToUnitMeasureGetDTO(unitMeasure);
    }

    @Caching(evict = {
            @CacheEvict(value = "unitmeasures", allEntries = true),
            @CacheEvict(value = "unitmeasure", key = "#id")
    })
    public void deleteUnitMeasure(UUID id) {
        unitMeasureExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
    }

    @Caching(evict = {
            @CacheEvict(value = "unitmeasures", allEntries = true),
            @CacheEvict(value = "unitmeasure", key = "#id")
    })
    public void inactiveUnitMeasure(UUID id) {
        unitMeasureExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
    }

    protected void unitMeasureExistsValidation(UUID id) {
        unitMeasureValidation.unitMeasureExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        unitMeasureRepository.updateEntityStatus(entityStatus, id);
    }

    protected UnitMeasure getUnitMeasureByIdForRelationship(UUID id) {
        return unitMeasureRepository.findById(id).orElseThrow(() -> new NotFoundException(getNotFoundMessage()));
    }

    private String getNotFoundMessage(){
        return messageUtils.getMessage("unit.measure.not-found");
    }
}
