package br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.NeighborhoodType;

import java.util.UUID;

public record AddressGetDTO(
        UUID id,

        String zipCode,

        String streetAddress,

        Integer number,

        NeighborhoodType neighborhoodType,

        String neighborhood,

        String complement,

        String state,

        String city,

        CreatedFor createdFor,

        UUID addressOf,

        EntityStatus entityStatus) {

}


