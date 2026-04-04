package br.com.c137.project.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;

import java.util.UUID;

public record UnitMeasureGetDTO(
        UUID id,

        String acronym,

        String description,

        EntityStatus entityStatus
) {
}
