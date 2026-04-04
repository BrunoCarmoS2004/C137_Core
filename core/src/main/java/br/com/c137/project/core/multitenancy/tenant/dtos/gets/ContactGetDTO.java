package br.com.c137.project.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;

import java.util.UUID;

public record ContactGetDTO(
        UUID id,

        String name,

        String telephone,

        String email,

        String position,

        Boolean sendInvoicePaymentSlip,

        CreatedFor createdFor,

        UUID contactOf,

        EntityStatus entityStatus) {
}
