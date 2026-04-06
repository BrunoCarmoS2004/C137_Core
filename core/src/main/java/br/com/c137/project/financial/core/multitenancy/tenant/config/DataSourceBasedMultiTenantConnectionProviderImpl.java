package br.com.c137.project.financial.core.multitenancy.tenant.config;

import br.com.c137.project.financial.core.exceptions.TenantSchemaNotReadyException;
import br.com.c137.project.financial.core.multitenancy.mastertenant.config.DBContextHolder;
import br.com.c137.project.financial.core.multitenancy.mastertenant.config.DataSourceUtil;
import br.com.c137.project.financial.core.multitenancy.mastertenant.enums.DatabaseStatus;
import br.com.c137.project.financial.core.multitenancy.mastertenant.models.UserTenant;
import br.com.c137.project.financial.core.multitenancy.mastertenant.repositorys.UserTenantRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Configuration
@Log
public class DataSourceBasedMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private Map<String, DataSource> dataSourcesMtApp = new TreeMap<>();

    @Autowired
    private UserTenantRepository userTenantRepository;

    @Autowired
    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

    @PostConstruct
    public void loadDataSources() {
        dataSourcesMtApp.put("financial_master", masterDataSource);
    }

    private static final String NOMEBANCO = "user_";


    @Override
    protected DataSource selectAnyDataSource() {
        if (!dataSourcesMtApp.isEmpty()) {
            return this.dataSourcesMtApp.values().iterator().next();
        }
        try {
            List<UserTenant> userTenants = userTenantRepository.findAll();
            for (UserTenant userTenant : userTenants) {
                dataSourcesMtApp.put(userTenant.getDbName(),
                        DataSourceUtil.createAndConfigureDataSource(userTenant));
            }
            return this.dataSourcesMtApp.values().iterator().next();
        } catch (Exception e) {
            log.warning("Repository not yet available, using masterDataSource as fallback: " + e.getMessage());
            return masterDataSource;
        }
    }

    @Override
    protected DataSource selectDataSource(Object tenantIdentifier) {
        try {
            if (tenantIdentifier == null) {
                log.info("Tenant error " + tenantIdentifier);
                throw new BadCredentialsException("invalid-argument-tenant");
            }
            if ("financial_master".equals(tenantIdentifier)) {
                log.info("Operation directed to the central bank (financial_master)");
                return this.dataSourcesMtApp.get("financial_master");
            }
            tenantIdentifier = initializeTenantIfLost(tenantIdentifier);
            if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
                String tenant = (String) tenantIdentifier;
                UUID dbUserId = UUID.fromString(tenant.replace(NOMEBANCO, ""));
                UserTenant novoTenant = userTenantRepository.findByDbUserId(dbUserId);
                if (novoTenant.getDatabaseStatus().equals(DatabaseStatus.NOT_CREATED)) {
                    throw new TenantSchemaNotReadyException(
                            String.format("The tenant database %s has not yet been created.", tenantIdentifier));
                }
                if (!this.dataSourcesMtApp.containsKey(novoTenant.getDbName())) {
                    dataSourcesMtApp.put(novoTenant.getDbName(),
                            DataSourceUtil.createAndConfigureDataSource(novoTenant));
                }
            }
            if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
                throw new UsernameNotFoundException(
                        String.format("Tenant not found after rescan, tenant=%s", tenantIdentifier));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.dataSourcesMtApp.get(tenantIdentifier);
    }

    private String initializeTenantIfLost(Object tenantIdentifier) {
        if (tenantIdentifier != DBContextHolder.getCurrentDb()) {
            tenantIdentifier = DBContextHolder.getCurrentDb();
        }
        return (String) tenantIdentifier;
    }
}
