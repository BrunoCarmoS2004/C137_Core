package br.com.c137.project.core.multitenancy.tenant.dtos.puts;

import br.com.c137.project.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.core.multitenancy.tenant.enums.NeighborhoodType;
import jakarta.validation.constraints.*;

import java.util.UUID;

public record AddressPutDTO(
        @NotBlank(message = "Zip code is required")
        @Pattern(regexp = "^\\d{8}$", message = "Zip code must contain exactly 8 digits")
        String zipCode,

        @NotBlank(message = "Street address is required")
        @Size(max = 255, message = "Street address is too long")
        String streetAddress,

        @NotNull(message = "Number is required")
        @Positive(message = "Number must be positive")
        Integer number,

        @NotNull(message = "Neighborhood type is required")
        NeighborhoodType neighborhoodType,

        @NotBlank(message = "Neighborhood is required")
        @Size(max = 100, message = "Neighborhood name is too long")
        String neighborhood,

        @Size(max = 100, message = "Complement is too long")
        String complement,

        @NotBlank(message = "State is required")
        @Size(min = 2, max = 2, message = "State must be the 2-letter abbreviation (UF)")
        String state,

        @NotBlank(message = "City is required")
        @Size(max = 100, message = "City name is too long")
        String city,

        @NotBlank(message = "City IBGE code is required")
        @Pattern(regexp = "^\\d+$", message = "IBGE code must be numeric")
        String cityIbge
) {}


