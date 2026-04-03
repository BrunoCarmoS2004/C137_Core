package br.com.c137.project.core.mappers;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.core.multitenancy.tenant.models.partner.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientGetDTO clientToClientGetDTO(Client client);
    Client postToClient(ClientPostDTO clientPostDTO);
    Client putToClient(ClientPutDTO clientPutDTO, @MappingTarget Client client);
}
