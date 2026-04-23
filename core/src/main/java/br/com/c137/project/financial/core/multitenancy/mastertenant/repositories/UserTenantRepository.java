package br.com.c137.project.financial.core.multitenancy.mastertenant.repositories;

import br.com.c137.project.financial.core.multitenancy.mastertenant.enums.DatabaseStatus;
import br.com.c137.project.financial.core.multitenancy.mastertenant.models.UserTenant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserTenantRepository extends JpaRepository<UserTenant, UUID> {
    UserTenant findByDbUserId(UUID id);

    List<UserTenant> findByDatabaseStatus(DatabaseStatus databaseStatus);

    @Modifying
    @Transactional
    @Query("UPDATE UserTenant ut SET ut.databaseStatus = :databaseStatus WHERE ut.id = :id")
    void updateDatabaseStatus(DatabaseStatus databaseStatus, UUID id);


}
