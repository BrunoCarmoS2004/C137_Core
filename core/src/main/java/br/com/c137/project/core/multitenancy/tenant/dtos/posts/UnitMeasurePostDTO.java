package br.com.c137.project.core.multitenancy.tenant.dtos.posts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnitMeasurePostDTO(
        //TODO, COLOCAR AS MENSAGENS EM INGLES
        @NotBlank(message = "A sigla é obrigatória")
        @Size(max = 10, message = "A sigla deve ter no máximo 10 caracteres")
        String acronym,

        @NotBlank(message = "A descrição é obrigatória")
        String description
) {
}
