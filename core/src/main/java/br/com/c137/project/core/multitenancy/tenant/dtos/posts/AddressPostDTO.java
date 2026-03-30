package br.com.c137.project.core.multitenancy.tenant.dtos.posts;

import br.com.c137.project.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.core.multitenancy.tenant.enums.NeighborhoodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddressPostDTO(
        //TODO, BOTAR AS MENSAGENS EM INGLES
        @NotBlank(message = "CEP é obrigatório")
        String zipCode,

        @NotBlank(message = "Logradouro é obrigatório")
        String streetAddress,

        @NotNull(message = "Número é obrigatório")
        Integer number,

        @NotNull(message = "Tipo de bairro é obrigatório")
        NeighborhoodType neighborhoodType,

        @NotBlank(message = "Bairro é obrigatório")
        String neighborhood,

        String complement,

        @NotBlank(message = "Estado é obrigatório")
        String state,

        @NotBlank(message = "Cidade é obrigatória")
        String city,

        @NotNull(message = "Definição de proprietário (Cliente/Fornecedor) é obrigatória")
        CreatedFor createdFor,

        @NotNull(message = "O ID do proprietário do endereço é obrigatório")
        UUID addressOf) {

}


