package br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreatedFor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ContactPostDTO(
        @NotBlank(message = "Contact name is required")
        @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
        String name,

        @NotBlank(message = "Contact telephone is required")
        String telephone,

        @NotBlank(message = "Contact email is required")
        @Email(message = "Invalid email format")
        @Size(max = 255, message = "Email is too long")
        String email,

        @NotBlank(message = "Position is required")
        @Size(max = 100, message = "Position must not exceed 100 characters")
        String position,

        @NotNull(message = "Please specify if invoice/payment slips should be sent")
        Boolean sendInvoicePaymentSlip,

        @NotNull(message = "Owner definition (Client/Supplier) is required")
        CreatedFor createdFor,

        @NotNull(message = "Owner ID (contactOf) is required")
        UUID contactOf
) {
}