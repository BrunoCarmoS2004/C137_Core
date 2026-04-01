package br.com.c137.project.core.mappers.address;

import br.com.c137.project.core.mappers.Mapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.core.multitenancy.tenant.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressAddressToGetMapper implements Mapper<Address, AddressGetDTO> {
    @Override
    public AddressGetDTO mapper(Address address) {
        return new AddressGetDTO(
                address.getId(),
                address.getZipCode(),
                address.getStreetAddress(),
                address.getNumber(),
                address.getNeighborhoodType(),
                address.getNeighborhood(),
                address.getComplement(),
                address.getState(),
                address.getCity(),
                address.getCreatedFor(),
                address.getAddressOf(),
                address.getEntityStatus()
        );
    }
}
