package br.com.c137.project.financial.core.multitenancy.tenant.models;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.getUserIdFromToken;

@Entity
@Table(name = "contacts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String position;

    @Column(name = "send_invoice_payment_slip", nullable = false)
    private Boolean sendInvoicePaymentSlip;

    @Enumerated(EnumType.STRING)
    @Column(name = "created_for", nullable = false)
    private CreatedFor createdFor; // Enum for define how is the own: Client or Supplier

    @Column(name = "contact_of_id", nullable = false)
    private UUID contactOf; //Client or Supplier Id

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_status", nullable = false)
    private EntityStatus entityStatus;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.entityStatus = EntityStatus.ACTIVE;
        this.createdBy = getUserIdFromToken();
    }
}
