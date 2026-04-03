package br.com.c137.project.core.multitenancy.tenant.repositorys;

import br.com.c137.project.core.multitenancy.tenant.enums.CreationStatus;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.partner.Supplier;
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
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    <T> Optional<T> findById(UUID uuid, Class<T> type);

    <T> Page<T> findBy(Pageable pageable, Class<T> type);

    boolean existsByInscription(String inscription);

    boolean existsByEmail(String inscription);

    boolean existsByInscriptionAndIdNot(String inscription, UUID id);

    boolean existsByEmailAndIdNot(String inscription, UUID id);

    @Transactional
    @Modifying
    @Query("UPDATE Supplier s SET s.creationStatus = :creationStatus WHERE s.id = :id")
    void updateCreationStatus(CreationStatus creationStatus, UUID id);

    @Transactional
    @Modifying
    @Query("UPDATE Supplier s SET s.entityStatus = :entityStatus WHERE s.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);
}
