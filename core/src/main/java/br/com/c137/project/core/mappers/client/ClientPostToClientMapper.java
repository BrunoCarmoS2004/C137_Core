package br.com.c137.project.core.mappers.client;

import br.com.c137.project.core.mappers.Mapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.posts.ClientPostDTO;
import br.com.c137.project.core.multitenancy.tenant.models.partner.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientPostToClientMapper implements Mapper<ClientPostDTO, Client> {
    @Override
    public Client mapper(ClientPostDTO clientPostDTO) {
        return new Client(
                clientPostDTO.name(),
                clientPostDTO.inscription(),
                clientPostDTO.inscriptionType(),
                clientPostDTO.inscriptionDate(),
                clientPostDTO.email(),
                clientPostDTO.telephone(),
                clientPostDTO.cellPhone(),
                clientPostDTO.accountingAccount(),
                clientPostDTO.stateRegistration(),
                clientPostDTO.stateRegistrationDate(),
                clientPostDTO.municipalRegistration()
        );
    }
}
