CREATE TABLE IF NOT EXISTS cnaes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    secao VARCHAR(10),
    divisao VARCHAR(10),
    grupo VARCHAR(10),
    classe VARCHAR(10),
    sub_classe VARCHAR(10),
    denominacao VARCHAR(255)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS unit_measures (
    id BINARY(16) PRIMARY KEY,
    acronym VARCHAR(10) NOT NULL UNIQUE,
    description VARCHAR(100) NOT NULL,
    created_by_id BINARY(16) NOT NULL DEFAULT 0x11111111111111111111111111111111,
    entity_status VARCHAR(20) NOT NULL
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS tax_rules (
    id BINARY(16) PRIMARY KEY,
    service_code VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    cnae_id BIGINT NOT NULL,
    municipal_code VARCHAR(50) NOT NULL,
    municipal_activity_code VARCHAR(50) NOT NULL,
    national_tax_code VARCHAR(50) NOT NULL,
    nbs VARCHAR(50) NOT NULL,
    pis DOUBLE,
    ir DOUBLE,
    confins DOUBLE,
    inss DOUBLE,
    csll DOUBLE,
    ibpt DOUBLE,
    initial_effective_date DATE,
    final_effective_date DATE,
    is_default BOOLEAN DEFAULT FALSE,
    tax_nature VARCHAR(100) NOT NULL,
    enforceability VARCHAR(100) NOT NULL,
    process_number VARCHAR(100),
    tax_type VARCHAR(50) NOT NULL,
    iss DOUBLE NOT NULL,
    allows_retention BOOLEAN DEFAULT FALSE,
    allows_deduction BOOLEAN DEFAULT FALSE,
    created_by_id BINARY(16) NOT NULL DEFAULT 0x11111111111111111111111111111111,
    created_at DATETIME NOT NULL,
    entity_status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_tax_rule_cnae FOREIGN KEY (cnae_id) REFERENCES cnaes(id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS clients (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    inscription VARCHAR(14) NOT NULL,
    inscription_type VARCHAR(20) NOT NULL,
    inscription_date DATE NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telephone VARCHAR(20) NOT NULL,
    cell_phone VARCHAR(20),
    accounting_account VARCHAR(50),
    state_registration VARCHAR(50),
    state_registration_date DATE,
    municipal_registration VARCHAR(50),
    created_by_id BINARY(16) NOT NULL DEFAULT 0x11111111111111111111111111111111,
    created_at DATETIME NOT NULL,
    creation_status VARCHAR(20) NOT NULL,
    entity_status VARCHAR(20) NOT NULL
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS suppliers (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    inscription VARCHAR(14) NOT NULL,
    inscription_type VARCHAR(20) NOT NULL,
    inscription_date DATE NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telephone VARCHAR(20) NOT NULL,
    cell_phone VARCHAR(20),
    accounting_account VARCHAR(50),
    state_registration VARCHAR(50),
    state_registration_date DATE,
    municipal_registration VARCHAR(50),
    created_by_id BINARY(16) NOT NULL DEFAULT 0x11111111111111111111111111111111,
    created_at DATETIME NOT NULL,
    entity_status VARCHAR(20) NOT NULL
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS services_products (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    code VARCHAR(100) NOT NULL UNIQUE,
    price DECIMAL(19, 2) NOT NULL,
    cost DECIMAL(19, 2),
    unit_measure_id BINARY(16),
    tax_rule_id BINARY(16) NOT NULL,
    supplier_id BINARY(16),
    description TEXT,
    created_by_id BINARY(16) NOT NULL DEFAULT 0x11111111111111111111111111111111,
    created_at DATETIME NOT NULL,
    entity_status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_sp_unit FOREIGN KEY (unit_measure_id) REFERENCES unit_measures(id),
    CONSTRAINT fk_sp_tax FOREIGN KEY (tax_rule_id) REFERENCES tax_rules(id),
    CONSTRAINT fk_sp_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS addresses (
    id BINARY(16) PRIMARY KEY,
    zip_code VARCHAR(8) NOT NULL,
    street_address VARCHAR(255) NOT NULL,
    number INT NOT NULL,
    neighborhood_type VARCHAR(50) NOT NULL,
    neighborhood VARCHAR(100) NOT NULL,
    complement VARCHAR(100),
    state CHAR(2) NOT NULL,
    city VARCHAR(100) NOT NULL,
    cityIbge VARCHAR(50) NOT NULL,
    created_for VARCHAR(30) NOT NULL,
    address_of_id BINARY(16) NOT NULL,
    created_by_id BINARY(16) NOT NULL DEFAULT 0x11111111111111111111111111111111,
    created_at DATETIME NOT NULL,
    entity_status VARCHAR(20) NOT NULL
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS contacts (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    email VARCHAR(150) NOT NULL,
    position VARCHAR(100) NOT NULL,
    send_invoice_payment_slip BOOLEAN DEFAULT FALSE,
    created_for VARCHAR(20) NOT NULL,
    contact_of_id BINARY(16) NOT NULL,
    created_by_id BINARY(16) NOT NULL DEFAULT 0x11111111111111111111111111111111,
    created_at DATETIME NOT NULL,
    entity_status VARCHAR(20) NOT NULL
    ) ENGINE=InnoDB;