package br.com.c137.project.financial.core.multitenancy.tenant.repositories;

import br.com.c137.project.financial.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.financial.core.multitenancy.tenant.models.serviceproduct.UnitMeasure;
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
public interface UnitMeasureRepository extends JpaRepository<UnitMeasure, UUID> {
    <T> Page<T> findBy(Pageable pageable, Class<T> type);

    <T> Optional<T> findById(UUID id, Class<T> type);

    @Transactional
    @Modifying
    @Query("UPDATE UnitMeasure u SET u.entityStatus = :entityStatus WHERE u.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);

    boolean existsByAcronym(String acronym);

    boolean existsByAcronymAndIdNot(String acronym, UUID id);
}
