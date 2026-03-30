package br.com.c137.project.core.mappers.client;

import br.com.c137.project.core.mappers.Mapper;
import br.com.c137.project.core.mappers.MapperPut;
import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.core.multitenancy.tenant.models.partner.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientPutToClientMapper implements MapperPut<ClientPutDTO, Client, Client> {
    @Override
    public Client mapper(ClientPutDTO clientPutDTO, Client client) {
        client.setName(clientPutDTO.name());
        client.setInscription(clientPutDTO.inscription());
        client.setInscriptionType(clientPutDTO.inscriptionType());
        client.setInscriptionDate(clientPutDTO.inscriptionDate());
        client.setEmail(clientPutDTO.email());
        client.setTelephone(clientPutDTO.telephone());
        client.setCellPhone(clientPutDTO.cellPhone());
        client.setAccountingAccount(clientPutDTO.accountingAccount());
        client.setStateRegistration(clientPutDTO.stateRegistration());
        client.setStateRegistrationDate(clientPutDTO.stateRegistrationDate());
        client.setMunicipalRegistration(clientPutDTO.municipalRegistration());
        return client;
    }
}
