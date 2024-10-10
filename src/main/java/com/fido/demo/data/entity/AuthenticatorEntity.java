package com.fido.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.UUID;

@Entity
@Table(name = "AUTHENTICATORS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatorEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "aaguid")
    private UUID aaguid;

    @Column(name = "credential_id")
    private int credentialId;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "transports")
    private String transports;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "firmware_version")
    private String firmwareVersion;

    @Column(name = "created_at")
    private Time createdAt;

    @Column(name = "updated_at")
    private Time updatedAt;
}
