package br.com.c137.project.core.mappers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ServiceProductGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.ServiceProductPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ServiceProductPutDTO;
import br.com.c137.project.core.multitenancy.tenant.models.ServiceProduct;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ServiceProductMapper {
    ServiceProductGetDTO serviceProductToServiceProductGetDTO(ServiceProduct serviceProduct);
    ServiceProduct postToServiceProduct(ServiceProductPostDTO serviceProductPostDTO);
    ServiceProduct putToServiceProduct(ServiceProductPutDTO serviceProductPutDTO,  @MappingTarget ServiceProduct serviceProduct);
}
