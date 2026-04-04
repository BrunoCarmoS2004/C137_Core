package br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.InscriptionType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ClientPutDTO(
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
        String name,

        @NotBlank(message = "Inscription is required")
        @Pattern(regexp = "\\d+", message = "Inscription must contain only numbers")
        String inscription,

        @NotNull(message = "Inscription type is required")
        InscriptionType inscriptionType,

        @NotNull(message = "Inscription date is required")
        @PastOrPresent(message = "Inscription date cannot be in the future")
        LocalDate inscriptionDate,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Telephone is required")
        String telephone,

        String cellPhone,

        @Size(max = 20, message = "Accounting account is too long")
        String accountingAccount,

        String stateRegistration,

        @PastOrPresent(message = "State registration date cannot be in the future")
        LocalDate stateRegistrationDate,

        String municipalRegistration
) {
}
