package br.com.c137.project.financial.core.multitenancy.tenant.repositories;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.serviceproduct.ServiceProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceProductRepository extends JpaRepository<ServiceProduct, UUID> {
    <T> Optional<T> findById(UUID uuid, Class<T> type);

    <T> Page<T> findBy(Pageable pageable, Class<T> type);

    boolean existsByCodeAndIdNot(String code, UUID id);

    boolean existsByCode(String code);

    @Transactional
    @Modifying
    @Query("UPDATE ServiceProduct sp SET sp.entityStatus = :entityStatus WHERE sp.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);
}
