package br.com.c137.project.core.mappers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.AddressPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.AddressPutDTO;
import br.com.c137.project.core.multitenancy.tenant.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressGetDTO addressToAddressGetDTO(Address address);
    Address postToAddress(AddressPostDTO address);
    Address putToAddress(AddressPutDTO addressPutDTO, @MappingTarget Address address);
}
