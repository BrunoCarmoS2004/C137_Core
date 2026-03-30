package br.com.c137.project.core.multitenancy.tenant.models.partner;

import br.com.c137.project.core.multitenancy.tenant.dtos.puts.ClientPutDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.InscriptionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "clients")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class Client extends Partner {
    public Client(
            String name,
            String inscription,
            InscriptionType inscriptionType,
            LocalDate inscriptionDate,
            String email,
            String telephone,
            String cellPhone,
            String accountingAccount,
            String stateRegistration,
            LocalDate stateRegistrationDate,
            String municipalRegistration
    ) {
        super(
                name,
                inscription,
                inscriptionType,
                inscriptionDate,
                email,
                telephone,
                cellPhone,
                accountingAccount,
                stateRegistration,
                stateRegistrationDate,
                municipalRegistration
        );
    }
}
