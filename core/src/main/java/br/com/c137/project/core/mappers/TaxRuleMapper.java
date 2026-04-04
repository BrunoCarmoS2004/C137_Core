package br.com.c137.project.core.mappers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.TaxRuleGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.TaxRulePostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.TaxRulePutDTO;
import br.com.c137.project.core.multitenancy.tenant.models.TaxRule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaxRuleMapper {
    TaxRuleGetDTO taxRuleToTaxRuleGetDTO(TaxRule taxRule);
    TaxRule postToTaxRule(TaxRulePostDTO taxRulePostDTO);
    TaxRule putToTaxRule(TaxRulePutDTO taxRulePutDTO,  @MappingTarget TaxRule taxRule);
}
