-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE RELYING_PARTIES (
    --id UUID PRIMARY KEY DEFAULT uuid_generate_v1(),  -- Time-based UUID
    id SERIAL PRIMARY KEY,
    rp_id VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,  -- Non-nullable name
    origin VARCHAR(255) NOT NULL,
    icon_url VARCHAR(512),
    description TEXT,  -- Nullable description
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Table: RP_CONFIGURATIONS
CREATE TABLE RELYING_PARTY_CONFIGS (
    id SERIAL PRIMARY KEY,
    rp_id INTEGER REFERENCES RP(id) ON DELETE CASCADE,
    setting_key VARCHAR(255) NOT NULL,
    setting_value TEXT,
    UNIQUE (rp_id, setting_key)
);


-- Table: USERS
CREATE TABLE USERS (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE,
    display_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);


-- Table: credentials
CREATE TABLE CREDENTIALS (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES USERS(id) ON DELETE CASCADE,
    rp_id INTEGER REFERENCES RP(id) ON DELETE CASCADE,
    authenticator_credential_id BYTEA UNIQUE NOT NULL,
    public_key BYTEA NOT NULL,
    sign_count BIGINT DEFAULT 0,
    transports VARCHAR(255)[],
    attestation_format VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Indexes
CREATE INDEX idx_credentials_rp_id ON credentials(rp_id);
CREATE INDEX idx_credentials_user_id ON credentials(user_id);
CREATE INDEX idx_credentials_attestation_format ON credentials(attestation_format);


-- Table: credential_configs
CREATE TABLE credential_configs (
    id SERIAL PRIMARY KEY,
    credential_id INTEGER REFERENCES credentials(id) ON DELETE CASCADE,
    setting_key VARCHAR(255) NOT NULL,
    setting_value TEXT,
    UNIQUE (credential_id, setting_key)
);

CREATE INDEX idx_credential_configs_credential_id ON credential_configs(credential_id);
--CREATE INDEX idx_credential_configs_setting_key ON credential_configs(setting_key);


-- Table: attestation_formats
CREATE TABLE attestation_formats (
    id SERIAL PRIMARY KEY,
    format_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- Table: authenticators
CREATE TABLE authenticators (
    id SERIAL PRIMARY KEY,
    credential_id INTEGER REFERENCES credentials(id) ON DELETE CASCADE,
    aaguid UUID,
    device_type VARCHAR(50),
    transports VARCHAR(255)[],
    manufacturer VARCHAR(255),
    model VARCHAR(255),
    firmware_version VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Table: sessions
CREATE TABLE sessions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
    session_token UUID UNIQUE NOT NULL DEFAULT uuid_generate_v4(),
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
