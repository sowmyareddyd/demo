package com.fido.demo.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "RELYING_PARTIES")
@Data
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
}
