package br.com.c137.project.core.multitenancy.tenant.dtos.puts;

import br.com.c137.project.core.multitenancy.tenant.enums.InscriptionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ClientPutDTO(
        //TODO, COLOCAR AS MENSAGENS EM INGLES
        @NotBlank(message = "O nome é obrigatório")
        String name,

        @NotBlank(message = "A inscrição é obrigatória")
        String inscription,

        @NotNull(message = "O tipo de inscrição é obrigatório")
        InscriptionType inscriptionType,

        @NotNull(message = "A data de inscrição é obrigatória")
        LocalDate inscriptionDate,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "O telefone é obrigatório")
        String telephone,

        String cellPhone,

        String accountingAccount,

        String stateRegistration,

        LocalDate stateRegistrationDate,

        String municipalRegistration
) {
}
