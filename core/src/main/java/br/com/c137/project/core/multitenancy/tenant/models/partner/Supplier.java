package br.com.c137.project.core.multitenancy.tenant.models.partner;

import br.com.c137.project.core.multitenancy.tenant.enums.InscriptionType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "suppliers")
@Data
@SuperBuilder // E aqui também
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Supplier extends Partner {
    public Supplier(
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
