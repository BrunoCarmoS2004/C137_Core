package br.com.c137.project.core.multitenancy.tenant.dtos.puts;

import br.com.c137.project.core.multitenancy.tenant.enums.ServiceProductType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceProductPutDTO(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @NotNull(message = "Type (Service or Product) is required")
        ServiceProductType type,

        @NotBlank(message = "Code is required")
        @Size(max = 60, message = "Code must not exceed 60 characters")
        String code,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        BigDecimal price,

        @PositiveOrZero(message = "Cost must not be negative")
        BigDecimal cost,

        @NotNull(message = "Unit of measure ID is required")
        UUID unitMeasureId,

        @NotNull(message = "Tax rule ID is required")
        UUID taxRuleId,

        @Size(max = 1000, message = "Description is too long")
        String description,

        UUID supplierId
) {
}
