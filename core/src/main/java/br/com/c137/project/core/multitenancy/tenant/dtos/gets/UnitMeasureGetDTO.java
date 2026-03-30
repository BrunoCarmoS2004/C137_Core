package br.com.c137.project.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UnitMeasureGetDTO(
        UUID id,

        String acronym,

        String description,

        EntityStatus entityStatus
) {
}
