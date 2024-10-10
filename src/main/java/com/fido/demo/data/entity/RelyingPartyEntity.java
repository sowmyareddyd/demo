package com.fido.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "RELYING_PARTIES")
//@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelyingPartyEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "rp_id")
    private String rpId;

    @Column(name = "name")
    private String name;

    @Column(name = "origin")
    private String origin;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Time createdAt;

    @Column(name = "updated_at")
    private Time updatedAt;

    @JoinColumn(name = "rp_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<RelyingPartyConfigEntity> configs;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<RelyingPartyConfigEntity> getConfigs() {
        return configs;
    }

    public void setConfigs(List<RelyingPartyConfigEntity> configs) {
        this.configs = configs;
    }
}
