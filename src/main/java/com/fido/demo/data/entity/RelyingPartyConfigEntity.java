package com.fido.demo.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RELYING_PARTY_CONFIGS")
//@Data
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelyingPartyId() {
        return relyingPartyId;
    }

    public void setRelyingPartyId(int relyingPartyId) {
        this.relyingPartyId = relyingPartyId;
    }

    public String getSettingKey() {
        return settingKey;
    }

    public void setSettingKey(String settingKey) {
        this.settingKey = settingKey;
    }

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }
}
