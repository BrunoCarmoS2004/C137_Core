package br.com.c137.project.core.mappers.address;

import br.com.c137.project.core.mappers.Mapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.AddressPostDTO;
import br.com.c137.project.core.multitenancy.tenant.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressPostToAddressMapper implements Mapper<AddressPostDTO, Address> {
    @Override
    public Address mapper(AddressPostDTO addressPostDTO) {
        return new Address(
                addressPostDTO.zipCode(),
                addressPostDTO.streetAddress(),
                addressPostDTO.number(),
                addressPostDTO.neighborhoodType(),
                addressPostDTO.neighborhood(),
                addressPostDTO.complement(),
                addressPostDTO.state(),
                addressPostDTO.city(),
                addressPostDTO.cityIbge(),
                addressPostDTO.createdFor(),
                addressPostDTO.addressOf()
        );
    }
}
