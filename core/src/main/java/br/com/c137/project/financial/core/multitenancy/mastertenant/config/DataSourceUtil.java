package br.com.c137.project.financial.core.multitenancy.mastertenant.config;

import br.com.c137.project.financial.core.multitenancy.mastertenant.models.UserTenant;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public final class DataSourceUtil {

    public static DataSource createAndConfigureDataSource(UserTenant userTenant) {
        HikariDataSource ds = new HikariDataSource();
        ds.setUsername(userTenant.getUserName());
        ds.setPassword(userTenant.getPassword());
        ds.setJdbcUrl(userTenant.getUrl());
        ds.setDriverClassName(userTenant.getDriverClass());
        ds.setMaximumPoolSize(2);
        ds.setMinimumIdle(0);
        ds.setIdleTimeout(3000);
        ds.setConnectionTimeout(10000);
        ds.setMaxLifetime(60000);
        String tenantConnectionPoolName = userTenant.getDbName() + "-connection-pool";
        ds.setPoolName(tenantConnectionPoolName);
        return ds;
    }

}
