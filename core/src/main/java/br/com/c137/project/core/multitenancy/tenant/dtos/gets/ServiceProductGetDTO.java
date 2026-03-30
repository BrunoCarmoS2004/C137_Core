package br.com.c137.project.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.enums.ServiceProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceProductGetDTO(
        UUID id,

        String name,

        ServiceProductType type,

        String code,

        BigDecimal price,

        BigDecimal cost,

        UUID unitMeasureId,

        UUID taxRuleId,

        String description,

        UUID supplierId,

        EntityStatus entityStatus
) {
}
