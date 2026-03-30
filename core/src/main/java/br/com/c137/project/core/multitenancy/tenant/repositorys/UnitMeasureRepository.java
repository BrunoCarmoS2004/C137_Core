package br.com.c137.project.core.multitenancy.tenant.repositorys;

import br.com.c137.project.core.multitenancy.tenant.models.UnitMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UnitMeasureRepository extends JpaRepository<UnitMeasure, UUID> {
}
