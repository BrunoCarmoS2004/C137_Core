 package br.com.c137.project.core.multitenancy.tenant.dtos.puts;

 import br.com.c137.project.core.multitenancy.tenant.enums.Enforceability;
 import br.com.c137.project.core.multitenancy.tenant.enums.TaxNature;
 import br.com.c137.project.core.multitenancy.tenant.enums.TaxType;
 import jakarta.validation.constraints.*;

 import java.time.LocalDate;

 public record TaxRulePutDTO(
        @NotBlank(message = "Service code is required")
        @Size(max = 50, message = "Service code is too long")
        String serviceCode,

        @NotBlank(message = "Rule description is required")
        String description,

        @NotNull(message = "CNAE ID is required")
        Long cnaeId,

        @NotBlank(message = "Municipal code is required")
        @Pattern(regexp = "\\d{7}", message = "Municipal code must be 7 digits (IBGE standard)")
        String municipalCode,

        @NotBlank(message = "Municipal activity code is required")
        String municipalActivityCode,

        @NotBlank(message = "National tax code is required")
        String nationalTaxCode,

        @NotBlank(message = "NBS is required")
        String nbs,

        @PositiveOrZero(message = "PIS must be positive")
        Double pis,

        @PositiveOrZero(message = "IR must be positive")
        Double ir,

        @PositiveOrZero(message = "COFINS must be positive")
        Double confins,

        @PositiveOrZero(message = "INSS must be positive")
        Double inss,

        @PositiveOrZero(message = "CSLL must be positive")
        Double csll,

        @PositiveOrZero(message = "IBPT percentage must be positive")
        Double ibpt,

        @NotNull(message = "Initial effective date is required")
        @FutureOrPresent(message = "Initial effective date must be today or in the future")
        LocalDate initialEffectiveDate,

        LocalDate finalEffectiveDate,

        Boolean isDefault,

        @NotNull(message = "Tax nature is required")
        TaxNature taxNature,

        @NotNull(message = "Enforceability is required")
        Enforceability enforceability,

        String processNumber,

        @NotNull(message = "Tax type is required")
        TaxType taxType,

        @NotNull(message = "ISS rate is required")
        Double iss,

        Boolean allowsRetention,
        Boolean allowsDeduction
) {
}
