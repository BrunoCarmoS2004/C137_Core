package br.com.c137.project.core.multitenancy.tenant.repositorys;

import br.com.c137.project.core.multitenancy.tenant.dtos.gets.AddressGetDTO;
import br.com.c137.project.core.multitenancy.tenant.enums.EntityStatus;
import br.com.c137.project.core.multitenancy.tenant.models.Address;
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
public interface AddressRepository extends JpaRepository<Address, UUID> {
    <T> Page<T> findBy(Pageable pageable, Class<T> type);

    <T> Optional<T> findById(UUID id, Class<T> type);

    Page<AddressGetDTO> findAllByAddressOf(UUID addressOf, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Address a SET a.entityStatus = :entityStatus WHERE a.id = :id")
    void updateEntityStatus(EntityStatus entityStatus, UUID id);

    boolean existsByZipCodeAndNumber(String zipCode, Integer number);

    boolean existsByZipCodeAndNumberAndIdNot(String zipCode, Integer number, UUID id);
}
