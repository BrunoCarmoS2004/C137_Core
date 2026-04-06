package br.com.c137.project.financial.core.multitenancy.tenant.models.partner;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.InscriptionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String inscription;

    @Enumerated(EnumType.STRING)
    @Column(name = "inscription_type", nullable = false)
    private InscriptionType inscriptionType;

    @Column(name = "inscription_date", nullable = false)
    private LocalDate inscriptionDate;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telephone;

    @Column(name = "cell_phone")
    private String cellPhone;

    @Column(name = "accounting_account")
    private String accountingAccount;

    @Column(name = "state_registration")
    private String stateRegistration;

    @Column(name = "state_registration_date")
    private LocalDate stateRegistrationDate;

    @Column(name = "municipal_registration")
    private String municipalRegistration;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "creation_status", nullable = false)
    private CreationStatus creationStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_status", nullable = false)
    private EntityStatus entityStatus;

    public Partner(String name,
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
        this.name = name;
        this.inscription = inscription;
        this.inscriptionType = inscriptionType;
        this.inscriptionDate = inscriptionDate;
        this.email = email;
        this.telephone = telephone;
        this.cellPhone = cellPhone;
        this.accountingAccount = accountingAccount;
        this.stateRegistration = stateRegistration;
        this.stateRegistrationDate = stateRegistrationDate;
        this.municipalRegistration = municipalRegistration;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.creationStatus = CreationStatus.INCOMPLETE;
        this.entityStatus = EntityStatus.ACTIVE;
    }

}
