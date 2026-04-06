package br.com.c137.project.financial.core.multitenancy.mastertenant.models;

import br.com.c137.project.financial.core.multitenancy.mastertenant.enums.DatabaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users_tenants", catalog = "financial_master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "db_name", nullable = false, updatable = false)
    private String dbName;

    @Column(name = "db_user_id", nullable = false, updatable = false)
    private UUID dbUserId;

    @Column(name = "url", nullable = false, updatable = false)
    private String url;

    @Column(name = "user_name", nullable = false, updatable = false)
    private String userName;

    @Column(name = "password", nullable = false, updatable = false)
    private String password;

    @Column(name = "driver_class", nullable = false, updatable = false)
    private String driverClass;

    @Enumerated(EnumType.STRING)
    @Column(name = "db_status", nullable = false)
    private DatabaseStatus databaseStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public UserTenant(UUID dbUserId, String ip, Integer port, String userName, String password) {
        this.dbName = "user_"+dbUserId.toString();
        this.dbUserId = dbUserId;
        this.url = "jdbc:mysql://"+ip+":"+port+"/"+this.dbName+"?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=America/Sao_Paulo";
        this.userName = userName;
        this.password = password;
        this.driverClass = "com.mysql.cj.jdbc.Driver";
        this.databaseStatus = DatabaseStatus.NOT_CREATED;
    }
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
