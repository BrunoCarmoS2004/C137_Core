package br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.financial.core.multitenancy.mastertenant.models.Cnae;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.Enforceability;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.TaxNature;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.TaxType;

import java.time.LocalDate;
import java.util.UUID;

public record TaxRuleGetDTO(
        UUID id,

        String serviceCode,

        String description,

        Cnae cnae,

        String municipalCode,

        String municipalActivityCode,

        String nationalTaxCode,

        String nbs,

        Double pis,

        Double ir,

        Double confins,

        Double inss,

        Double csll,

        Double ibpt,

        LocalDate initialEffectiveDate,

        LocalDate finalEffectiveDate,

        Boolean defoult,

        TaxNature taxNature,

        Enforceability enforceability,

        String processNumber,

        TaxType taxType,

        Double iss,

        Boolean allowsRetention,

        Boolean allowsDeduction,

        EntityStatus entityStatus
) {
}
