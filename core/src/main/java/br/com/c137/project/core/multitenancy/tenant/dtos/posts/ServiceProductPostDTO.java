package br.com.c137.project.core.multitenancy.tenant.dtos.posts;

import br.com.c137.project.core.multitenancy.tenant.enums.ServiceProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record ServiceProductPostDTO(
        //TODO, COLOCAR AS MENSAGENS EM INGLES
        @NotBlank(message = "O nome do produto/serviço é obrigatório")
        String name,

        @NotNull(message = "O tipo (Serviço ou Produto) é obrigatório")
        ServiceProductType type,

        @NotBlank(message = "O código é obrigatório")
        String code,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal price,

        @PositiveOrZero(message = "O custo não pode ser negativo")
        BigDecimal cost,

        @NotNull(message = "A unidade de medida é obrigatória")
        UUID unitMeasureId,

        @NotNull(message = "A regra tributária é obrigatória")
        UUID taxRuleId,

        String description,

        UUID supplierId // Opcional conforme regra de negócio
) {
}
