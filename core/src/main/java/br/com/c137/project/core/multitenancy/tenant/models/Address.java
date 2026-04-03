package br.com.c137.project.core.multitenancy.tenant.models;

import br.com.c137.project.core.multitenancy.tenant.enums.CreatedFor;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.enums.NeighborhoodType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

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

//    @ManyToOne
//    @JoinColumn(name = "created_by_id", nullable = false)
//    private User createdBy;

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

    public Address(String zipCode, String streetAddress, Integer number, NeighborhoodType neighborhoodType, String neighborhood, String complement, String state, String city, String cityIbge, CreatedFor createdFor, UUID addressOf) {
        this.zipCode = zipCode;
        this.streetAddress = streetAddress;
        this.number = number;
        this.neighborhoodType = neighborhoodType;
        this.neighborhood = neighborhood;
        this.complement = complement;
        this.state = state;
        this.city = city;
        this.cityIbge = cityIbge;
        this.createdFor = createdFor;
        this.addressOf = addressOf;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.entityStatus = EntityStatus.ACTIVE;
    }
}
