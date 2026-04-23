package br.com.c137.project.financial.core.multitenancy.tenant.models;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.enums.NeighborhoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.c137.project.financial.core.utils.ServiceUtils.getUserIdFromToken;

@Entity
@Table(name = "addresses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @Column(name = "neighborhood_type", nullable = false)
    private NeighborhoodType neighborhoodType;

    @Column(nullable = false)
    private String neighborhood;

    private String complement; // Optional (Optional: Yes)

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    @Column(name = "city_ibge", nullable = false)
    private String cityIbge;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "created_for", nullable = false, updatable = false)
    private CreatedFor createdFor; // Enum for define how is the own: Client or Supplier

    @Column(name = "address_of_id", nullable = false, updatable = false)
    private UUID addressOf; // Client or Supplier ID

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
