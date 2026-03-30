package br.com.c137.project.core.multitenancy.tenant.dtos.posts;

import br.com.c137.project.core.multitenancy.tenant.enums.Enforceability;
import br.com.c137.project.core.multitenancy.tenant.enums.TaxNature;
import br.com.c137.project.core.multitenancy.tenant.enums.TaxType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record TaxRulePostDTO(
        //TODO, COLOCAR AS MENSAGENS EM INGLES
        @NotBlank(message = "Código do serviço é obrigatório")
        String serviceCode,

        @NotBlank(message = "Descrição da regra é obrigatória")
        String description,

        @NotNull(message = "CNAE é obrigatório")
        UUID cnaeId,

        @NotBlank(message = "Código municipal é obrigatório")
        String municipalCode,

        @NotBlank(message = "Código de atividade municipal é obrigatório")
        String municipalActivityCode,

        @NotBlank(message = "Código tributário nacional é obrigatório")
        String nationalTaxCode,

        @NotBlank(message = "NBS é obrigatório")
        String nbs,

        Double pis,
        Double ir,
        Double confins,
        Double inss,
        Double csll,
        Double ibpt,
        LocalDate initialEffectiveDate,
        LocalDate finalEffectiveDate,
        Boolean defoult,

        @NotNull(message = "Natureza da tributação é obrigatória")
        TaxNature taxNature,

        @NotNull(message = "Exigibilidade é obrigatória")
        Enforceability enforceability,

        String processNumber,

        @NotNull(message = "Tipo de imposto é obrigatório")
        TaxType taxType,

        @NotNull(message = "Alíquota ISS é obrigatória")
        Double iss,

        Boolean allowsRetention,
        Boolean allowsDeduction
) {
}
