package br.com.c137.project.financial.core.controllers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.UnitMeasureGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.UnitMeasurePostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.UnitMeasurePutDTO;
import br.com.c137.project.financial.core.responses.ResponsePayload;
import br.com.c137.project.financial.core.services.UnitMeasureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/unitmeasure")
public class UnitMeasureController {
    @Autowired
    private UnitMeasureService unitMeasureService;

    @GetMapping
    public ResponseEntity<?> getAll(@PageableDefault(sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable, PagedResourcesAssembler<UnitMeasureGetDTO> assembler) {
        return unitMeasureService.getAll(pageable, assembler);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> getUnitMeasureById(@PathVariable UUID id) {
        return unitMeasureService.getUnitMeasureById(id);
    }

    @PostMapping
    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> postUnitMeasure(@Valid @RequestBody UnitMeasurePostDTO unitMeasurePostDTO) {
        return unitMeasureService.postUnitMeasure(unitMeasurePostDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload<UnitMeasureGetDTO>> putUnitMeasure(@PathVariable UUID id, @Valid @RequestBody UnitMeasurePutDTO unitMeasurePutDTO) {
        return unitMeasureService.putUnitMeasure(id, unitMeasurePutDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitMeasure(@PathVariable UUID id) {
        return unitMeasureService.deleteUnitMeasure(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> inactiveUnitMeasure(@PathVariable UUID id) {
        return unitMeasureService.inactiveUnitMeasure(id);
    }
}
