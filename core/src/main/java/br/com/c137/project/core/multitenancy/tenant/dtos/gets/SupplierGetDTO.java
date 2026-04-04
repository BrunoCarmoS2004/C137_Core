package br.com.c137.project.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.enums.InscriptionType;

import java.time.LocalDate;
import java.util.UUID;

public record SupplierGetDTO(
        UUID id,

        String name,

        String inscription,

        InscriptionType inscriptionType,

        LocalDate inscriptionDate,

        String email,

        String telephone,

        String cellPhone,

        String accountingAccount,

        String stateRegistration,

        LocalDate stateRegistrationDate,

        String municipalRegistration,

        CreationStatus creationStatus,

        EntityStatus entityStatus) {
}
