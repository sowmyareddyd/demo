package com.fido.demo.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

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

    @Column(name = "credential_id")
    private byte[] credentialId;

    @Column(name = "public_key")
    private byte[] publicKey;

    @Column(name = "sign_count")
    private long sign_count;

    @Column(name = "attestation_format")
    private String attestationFormat;

    @Column(name = "created_at")
    private Time createdAt;

    @Column(name = "updated_at")
    private Time updatedAt;
}
