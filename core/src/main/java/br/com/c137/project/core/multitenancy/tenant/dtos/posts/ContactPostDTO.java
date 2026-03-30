package br.com.c137.project.core.multitenancy.tenant.dtos.posts;

import br.com.c137.project.core.multitenancy.tenant.enums.CreatedFor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ContactPostDTO(
        //TODO, COLOCAR AS MENSAGENS EM INGLES
        @NotBlank(message = "O nome do contato é obrigatório")
        String name,

        @NotBlank(message = "O telefone do contato é obrigatório")
        String telephone,

        @NotBlank(message = "O email do contato é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "O cargo é obrigatório")
        String position,

        @NotNull(message = "Defina se envia boletos/notas")
        Boolean sendInvoicePaymentSlip,

        @NotNull(message = "Definição de proprietário (Cliente/Fornecedor) é obrigatória")
        CreatedFor createdFor,

        @NotNull(message = "O ID do proprietário do contato é obrigatório")
        UUID contactOf) {
}
