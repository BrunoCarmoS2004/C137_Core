package br.com.c137.project.core.mappers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.SupplierGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.SupplierPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.SupplierPutDTO;
import br.com.c137.project.core.multitenancy.tenant.models.partner.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    SupplierGetDTO supplierToSupplierGetDTO(Supplier supplier);
    Supplier postToSupplier(SupplierPostDTO supplierPostDTO);
    Supplier putToSupplier(SupplierPutDTO  supplierPutDTO, @MappingTarget Supplier supplier);
}
