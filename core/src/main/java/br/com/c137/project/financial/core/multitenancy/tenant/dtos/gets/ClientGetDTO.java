package br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.InscriptionType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ClientGetDTO(
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

        LocalDateTime createdAt,

        CreationStatus creationStatus,

        EntityStatus entityStatus
        ) {
}
