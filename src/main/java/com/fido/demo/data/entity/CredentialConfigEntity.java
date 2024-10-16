package com.fido.demo.data.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Time;

@Entity
@Table(name = "CREDENTIAL_CONFIGS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialConfigEntity {

    @Id
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "credential_id")
    private int credentialId;

    @Column(name = "setting_key")
    private String settingKey;

    @Column(name = "setting_value")
    private String settingValue;

    @Column(name = "created_at")
    private Time createdAt;

    @Column(name = "updated_at")
    private Time updatedAt;

}
