package com.fido.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

import java.sql.Time;
import java.util.UUID;

@Entity
@Table(name = "AUTHENTICATORS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatorEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authenticator_sequence_generator")
    @SequenceGenerator(name = "authenticator_sequence_generator", sequenceName = "authenticators_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "aaguid")
    private UUID aaguid;

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


    @JoinColumn(name = "credential_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CredentialEntity credential;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getAaguid() {
        return aaguid;
    }

    public void setAaguid(UUID aaguid) {
        this.aaguid = aaguid;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getTransports() {
        return transports;
    }

    public void setTransports(String transports) {
        this.transports = transports;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
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
