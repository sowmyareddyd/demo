package com.fido.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "CREDENTIALS")
@Data
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
}
