package br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.ServiceProductType;
import br.com.c137.project.financial.core.multitenancy.tenant.models.TaxRule;
import br.com.c137.project.financial.core.multitenancy.tenant.models.UnitMeasure;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceProductGetDTO(
        UUID id,

        String name,

        ServiceProductType type,

        String code,

        BigDecimal price,

        BigDecimal cost,

        UnitMeasure unitMeasure,

        TaxRule taxRule,

        String description,

        UUID supplierId,

        EntityStatus entityStatus
) {
}
