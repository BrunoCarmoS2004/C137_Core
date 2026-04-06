package br.com.c137.project.financial.core.multitenancy.tenant.config;

import br.com.c137.project.financial.core.multitenancy.mastertenant.config.DBContextHolder;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;

public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

	private static final String DEFAULT_TENANT_ID = "financial_master";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenant = DBContextHolder.getCurrentDb();
        return StringUtils.hasText(tenant) ? tenant : DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
	
}
