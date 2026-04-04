package br.com.c137.project.core.multitenancy.tenant.dtos.puts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UnitMeasurePutDTO(
        @NotBlank(message = "Acronym is required")
        @Size(min = 1, max = 6, message = "Acronym must be between 1 and 6 characters")
        @Pattern(regexp = "^[A-Z0-9]{1,6}$", message = "Acronym must be uppercase alphanumeric without spaces")
        String acronym,

        @NotBlank(message = "Description is required")
        @Size(max = 100, message = "Description must not exceed 100 characters")
        String description
) {
}
