package br.com.c137.project.core.mappers.client;

import br.com.c137.project.core.mappers.Mapper;
import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ClientGetDTO;
import br.com.c137.project.core.multitenancy.tenant.models.partner.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientClientToGetMapper implements Mapper<Client, ClientGetDTO> {
    @Override
    public ClientGetDTO mapper(Client client) {
        return new ClientGetDTO(
                client.getId(),
                client.getName(),
                client.getInscription(),
                client.getInscriptionType(),
                client.getInscriptionDate(),
                client.getEmail(),
                client.getTelephone(),
                client.getCellPhone(),
                client.getAccountingAccount(),
                client.getStateRegistration(),
                client.getStateRegistrationDate(),
                client.getMunicipalRegistration(),
                client.getCreatedAt(),
                client.getCreationStatus(),
                client.getEntityStatus()
        );
    }
}
