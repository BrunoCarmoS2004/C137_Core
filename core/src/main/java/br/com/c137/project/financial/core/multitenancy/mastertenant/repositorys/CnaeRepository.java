package br.com.c137.project.financial.core.multitenancy.mastertenant.repositorys;

import br.com.c137.project.financial.core.multitenancy.mastertenant.models.Cnae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnaeRepository extends JpaRepository<Cnae, Long> {
}
