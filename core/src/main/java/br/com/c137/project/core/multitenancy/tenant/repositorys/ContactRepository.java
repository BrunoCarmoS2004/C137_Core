package br.com.c137.project.core.multitenancy.tenant.repositorys;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.ContactGetDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.Contact;
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
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    <T> Page<T> findBy(Pageable pageable, Class<T> type);

    <T> Optional<T> findById(UUID id, Class<T> type);

    Page<ContactGetDTO> findAllByContactOf(UUID contactOf, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.entityStatus = :entityStatus WHERE c.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);

    boolean existsByTelephoneAndIdNot(String telephone, UUID id);

    boolean existsByTelephone(String telephone);
}
