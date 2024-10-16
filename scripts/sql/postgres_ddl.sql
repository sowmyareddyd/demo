-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--DROP TABLE RELYING_PARTIES;
CREATE TABLE RELYING_PARTIES (
    --id UUID PRIMARY KEY DEFAULT uuid_generate_v1(),  -- Time-based UUID
    id BIGSERIAL PRIMARY KEY,
    external_id VARCHAR(255) UNIQUE NOT NULL, -- passed by clients in the requests
    name VARCHAR(255) NOT NULL,  -- Non-nullable name
    origin VARCHAR(255) NOT NULL,
    icon_url VARCHAR(512),
    description TEXT,  -- Nullable description
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);


-- Table: RP_CONFIGURATIONS
CREATE TABLE RELYING_PARTY_CONFIGS (
    id BIGSERIAL PRIMARY KEY,
    rp_id BIGSERIAL REFERENCES RELYING_PARTIES(id) ON DELETE CASCADE,
    setting_key VARCHAR(255) NOT NULL,
    setting_value TEXT,
    UNIQUE (rp_id, setting_key)
);


-- Table: USERS
--DROP TABLE USERS;
CREATE TABLE USERS (
    id BIGSERIAL PRIMARY KEY,
    external_id VARCHAR(255) UNIQUE  NOT NULL, -- passed by clients in the requests
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE,
    display_name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Table: sessions
--DROP TABLE SESSIONS;
CREATE TABLE sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL REFERENCES users(id) ON DELETE CASCADE,
    session_token UUID UNIQUE NOT NULL DEFAULT uuid_generate_v4(),
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);


-- Table: authenticators
CREATE TABLE authenticators (
    id BIGSERIAL PRIMARY KEY,
    aaguid UUID,
    device_type VARCHAR(50),
    transports VARCHAR(255)[],
    manufacturer VARCHAR(255),
    model VARCHAR(255),
    firmware_version VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);


-- Table: credentials
CREATE TABLE CREDENTIALS (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGSERIAL REFERENCES USERS(id) ON DELETE CASCADE,
    rp_id BIGSERIAL REFERENCES RELYING_PARTIES(id) ON DELETE CASCADE,
    authenticator_credential_id BYTEA UNIQUE NOT NULL, -- generated by authenticator for every key pair
    authenticator_id BIGSERIAL REFERENCES AUTHENTICATORS(id) on DELETE CASCADE,
    public_key BYTEA NOT NULL,
    sign_count BIGINT DEFAULT 0,
    transports VARCHAR(255)[],
    attestation_format VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Indexes (one multicolumn index on rp_id & user_id) and single column on user_id
CREATE INDEX idx_credentials_rp_id ON credentials(rp_id, user_id);
CREATE INDEX idx_credentials_user_id ON credentials(user_id);


-- Table: credential_configs
CREATE TABLE credential_configs (
    id BIGSERIAL PRIMARY KEY,
    credential_id BIGSERIAL REFERENCES credentials(id) ON DELETE CASCADE,
    setting_key VARCHAR(255) NOT NULL,
    setting_value TEXT,
    UNIQUE (credential_id, setting_key)
);

CREATE INDEX idx_credential_configs_credential_id ON credential_configs(credential_id);

-- Table: attestation_formats
CREATE TABLE attestation_formats (
    id SERIAL PRIMARY KEY,
    format_name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

