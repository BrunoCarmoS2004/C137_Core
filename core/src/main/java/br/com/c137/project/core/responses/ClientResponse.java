package br.com.c137.project.core.responses;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ClientGetDTO;

import java.util.UUID;

public record ClientResponse(
        UUID uuid,
        String message,
        ClientGetDTO clientGetDTO
) {
}
