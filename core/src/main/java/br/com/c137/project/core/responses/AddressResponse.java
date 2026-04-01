package br.com.c137.project.core.responses;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.AddressGetDTO;

import java.util.UUID;

public record AddressResponse(
        UUID id,
        String message,
        AddressGetDTO addressGetDTO
) {
}
