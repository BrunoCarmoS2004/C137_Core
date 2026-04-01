package br.com.c137.project.core.mappers.address;

import br.com.c137.project.core.mappers.Mapper;
import br.com.c137.project.core.mappers.MapperPut;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.AddressPutDTO;
import br.com.c137.project.core.multitenancy.tenant.models.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressPutToAddressMapper implements MapperPut<AddressPutDTO, Address> {
    @Override
    public Address mapper(AddressPutDTO addressPutDTO, Address address) {
        address.setZipCode(address.getZipCode());
        address.setStreetAddress(address.getStreetAddress());
        address.setNumber(address.getNumber());
        address.setNeighborhoodType(address.getNeighborhoodType());
        address.setNeighborhood(address.getNeighborhood());
        address.setComplement(address.getComplement());
        address.setState(address.getState());
        address.setCity(address.getCity());
        address.setCityIbge(address.getCityIbge());
        return address;
    }
}
