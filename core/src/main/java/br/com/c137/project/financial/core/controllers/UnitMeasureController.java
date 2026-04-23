package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.UnitMeasureGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.UnitMeasurePostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.UnitMeasurePutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.UnitMeasureService;
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
@RequestMapping("/unitmeasure")
public class UnitMeasureController {
    @Autowired
    private UnitMeasureService unitMeasureService;

    @Autowired
    private MessageUtils messageUtils;

    @GetMapping
    public ResponseEntity<PagedModel<UnitMeasureGetDTO>> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(unitMeasureService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> getUnitMeasureById(@PathVariable UUID id) {
        UnitMeasureGetDTO unitMeasureGetDTO = unitMeasureService.getUnitMeasureById(id);
        return createResponse(
                HttpStatus.OK,
                unitMeasureGetDTO.id(),
                unitMeasureGetDTO,
                messageUtils.getMessage("unit.measure.found")
        );
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> postUnitMeasure(@Valid @RequestBody UnitMeasurePostDTO unitMeasurePostDTO) {
        UnitMeasureGetDTO unitMeasureGetDTO = unitMeasureService.postUnitMeasure(unitMeasurePostDTO);
        return createResponse(
                HttpStatus.CREATED,
                unitMeasureGetDTO.id(),
                unitMeasureGetDTO,
                messageUtils.getMessage("unit.measure.created")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> putUnitMeasure(@PathVariable UUID id, @Valid @RequestBody UnitMeasurePutDTO unitMeasurePutDTO) {
        UnitMeasureGetDTO unitMeasureGetDTO = unitMeasureService.putUnitMeasure(id, unitMeasurePutDTO);
        return createResponse(
                HttpStatus.OK,
                unitMeasureGetDTO.id(),
                unitMeasureGetDTO,
                messageUtils.getMessage("unit.measure.updated")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitMeasure(@PathVariable UUID id) {
        unitMeasureService.deleteUnitMeasure(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveUnitMeasure(@PathVariable UUID id) {
        unitMeasureService.inactiveUnitMeasure(id);
        return ResponseEntity.noContent().build();
    }
}
