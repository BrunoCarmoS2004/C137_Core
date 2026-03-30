package br.com.c137.project.core.multitenancy.tenant.models;

import br.com.c137.project.core.multitenancy.mastertenant.models.Cnae;
import br.com.c137.project.core.multitenancy.tenant.enums.Enforceability;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.enums.TaxNature;
import br.com.c137.project.core.multitenancy.tenant.enums.TaxType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tax_rules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxRule {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "service_code", nullable = false)
    private String serviceCode;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "cnae_id", nullable = false)
    private Cnae cnae;

    @Column(name = "municipal_code", nullable = false)
    private String municipalCode;

    @Column(name = "municipal_activity_code", nullable = false)
    private String municipalActivityCode;

    @Column(name = "national_tax_code", nullable = false)
    private String nationalTaxCode;

    @Column(nullable = false)
    private String nbs;

    // Alíquotas (Optional: Yes)
    private Double pis;
    private Double ir;
    private Double confins;
    private Double inss;
    private Double csll;
    private Double ibpt;

    @Column(name = "initial_effective_date")
    private LocalDate initialEffectiveDate;

    @Column(name = "final_effective_date")
    private LocalDate finalEffectiveDate;

    @Column(name = "is_default")
    private Boolean defoult; // Mantido o nome conforme a tabela (default é palavra reservada)

//    @ManyToOne
//    @JoinColumn(name = "created_by_id", nullable = false)
//    private User createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_status", nullable = false)
    private EntityStatus entityStatus;
// --- Attributes ISS (Same Tabela) ---

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_nature", nullable = false)
    private TaxNature taxNature;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enforceability enforceability;

    @Column(name = "process_number")
    private String processNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type", nullable = false)
    private TaxType taxType;

    @Column(nullable = false)
    private Double iss;

    @Column(name = "allows_retention")
    private Boolean allowsRetention;

    @Column(name = "allows_deduction")
    private Boolean allowsDeduction;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.entityStatus = EntityStatus.ACTIVE;
    }
}
