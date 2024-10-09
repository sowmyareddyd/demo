package com.fido.demo.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RELYING_PARTY_CONFIGS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelyingPartyConfigEntity {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "rp_id")
    private int relyingPartyId;

    @Column(name = "setting_key")
    private String settingKey;

    @Column(name = "setting_value")
    private String settingValue;
}
