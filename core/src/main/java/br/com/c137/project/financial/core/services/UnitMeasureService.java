package br.com.c137.project.financial.core.services;

import br.com.c137.project.financial.core.exceptions.NotFoundException;
import br.com.c137.project.financial.core.mappers.UnitMeasureMapper;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.UnitMeasureGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.UnitMeasurePostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.UnitMeasurePutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.UnitMeasure;
import br.com.c137.project.financial.core.multitenancy.tenant.repositorys.UnitMeasureRepository;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.validations.UnitMeasureValidation;
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
public class UnitMeasureService {
    @Autowired
    private UnitMeasureRepository unitMeasureRepository;
    
    @Autowired
    private UnitMeasureMapper unitMeasureMapper;
    
    @Autowired
    private UnitMeasureValidation unitMeasureValidation;

    private final String unitMeasureNotFoundMessage = "Unit Measure not found";

    private final String unitMeasureCreatedMessage = "Unit Measure Created";

    private final String unitMeasureFoundMessage = "Unit Measure Found";

    private final String unitMeasureUpdatedMessage = "Unit Measure Updated";
    
    public ResponseEntity<?> getAll(Pageable pageable, PagedResourcesAssembler<UnitMeasureGetDTO> assembler) {
        Page<UnitMeasureGetDTO> unitMeasures = unitMeasureRepository.findBy(pageable, UnitMeasureGetDTO.class);
        return pageHasContent(unitMeasures, assembler);
    }
    
    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> getUnitMeasureById(UUID id) {
        UnitMeasureGetDTO unitMeasureGetDTO = unitMeasureRepository.findById(id, UnitMeasureGetDTO.class).orElseThrow(() -> new NotFoundException(unitMeasureNotFoundMessage));
        return createResponse(HttpStatus.OK, id, unitMeasureGetDTO, unitMeasureFoundMessage);
    }

    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> postUnitMeasure(UnitMeasurePostDTO unitMeasurePostDTO) {
        unitMeasureValidation.acronymExistValidation(unitMeasurePostDTO.acronym());
        UnitMeasure unitMeasure = unitMeasureMapper.postToUnitMeasure(unitMeasurePostDTO);
        unitMeasureRepository.save(unitMeasure);
        UnitMeasureGetDTO unitMeasureGetDTO = unitMeasureMapper.unitMeasureToUnitMeasureGetDTO(unitMeasure);
        return createResponse(HttpStatus.CREATED, unitMeasureGetDTO.id(), unitMeasureGetDTO, unitMeasureCreatedMessage);
    }

    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> putUnitMeasure(UUID id, UnitMeasurePutDTO unitMeasurePutDTO) {
        unitMeasureValidation.acronymExistInOtherIdValidation(unitMeasurePutDTO.acronym(), id);
        UnitMeasure unitMeasure = unitMeasureRepository.findById(id).orElseThrow(() -> new NotFoundException(unitMeasureNotFoundMessage));
        unitMeasure = unitMeasureMapper.putToUnitMeasure(unitMeasurePutDTO, unitMeasure);
        unitMeasureRepository.save(unitMeasure);
        UnitMeasureGetDTO unitMeasureGetDTO = unitMeasureMapper.unitMeasureToUnitMeasureGetDTO(unitMeasure);
        return createResponse(HttpStatus.OK, unitMeasureGetDTO.id(), unitMeasureGetDTO, unitMeasureUpdatedMessage);
    }

    public ResponseEntity<Void> deleteUnitMeasure(UUID id) {
        unitMeasureExistsValidation(id);
        updateEntityStatus(EntityStatus.DELETED, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Void> inactiveUnitMeasure(UUID id) {
        unitMeasureExistsValidation(id);
        updateEntityStatus(EntityStatus.INACTIVE, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected void unitMeasureExistsValidation(UUID id) {
        unitMeasureValidation.unitMeasureExistsValidation(id);
    }

    protected void updateEntityStatus(EntityStatus entityStatus, UUID id) {
        unitMeasureRepository.updateEntityStatus(entityStatus, id);
    }

    protected UnitMeasure getUnitMeasureByIdForRelationship(UUID id) {
        return unitMeasureRepository.findById(id).orElseThrow(() -> new NotFoundException(unitMeasureNotFoundMessage));
    }
}
