package com.fido.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "CREDENTIALS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredentialEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "rp_id")
    private int rpId;

    @Column(name = "authenticator_credential_id")
    private byte[] authenticatorCredentialId;

    @Column(name = "public_key")
    private byte[] publicKey;

    @Column(name = "sign_count")
    private long sign_count;

    @Column(name = "attestation_format")
    private String attestationFormat;

    @JoinColumn(name = "credential_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<CredentialConfigEntity> configs;

    @JoinColumn(name = "credential_id")
    @OneToOne(fetch = FetchType.LAZY)
    private AuthenticatorEntity authenticator;

    @Column(name = "created_at")
    private Time createdAt;

    @Column(name = "updated_at")
    private Time updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRpId() {
        return rpId;
    }

    public void setRpId(int rpId) {
        this.rpId = rpId;
    }

    public byte[] getAuthenticatorCredentialId() {
        return authenticatorCredentialId;
    }

    public void setAuthenticatorCredentialId(byte[] authenticatorCredentialId) {
        this.authenticatorCredentialId = authenticatorCredentialId;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public long getSign_count() {
        return sign_count;
    }

    public void setSign_count(long sign_count) {
        this.sign_count = sign_count;
    }

    public String getAttestationFormat() {
        return attestationFormat;
    }

    public void setAttestationFormat(String attestationFormat) {
        this.attestationFormat = attestationFormat;
    }

    public List<CredentialConfigEntity> getConfigs() {
        return configs;
    }

    public void setConfigs(List<CredentialConfigEntity> configs) {
        this.configs = configs;
    }

    public AuthenticatorEntity getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(AuthenticatorEntity authenticator) {
        this.authenticator = authenticator;
    }

    public Time getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Time createdAt) {
        this.createdAt = createdAt;
    }

    public Time getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Time updatedAt) {
        this.updatedAt = updatedAt;
    }
}
