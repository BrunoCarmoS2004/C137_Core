package br.com.c137.project.financial.core.mappers;

import br.com.c137.project.financial.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.financial.core.multitenancy.tenant.models.partner.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientGetDTO clientToClientGetDTO(Client client);
    Client postToClient(ClientPostDTO clientPostDTO);
    Client putToClient(ClientPutDTO clientPutDTO, @MappingTarget Client client);
}
