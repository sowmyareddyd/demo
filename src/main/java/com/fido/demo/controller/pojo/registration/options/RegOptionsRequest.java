package com.fido.demo.controller.pojo.registration.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fido.demo.controller.pojo.registration.RP;
import com.fido.demo.controller.pojo.registration.User;


//@Data // for getters and setters
//@NoArgsConstructor
public class RegOptionsRequest {

    @JsonProperty("rp")
    private RP rp;

    @JsonProperty("user")
    private User user;

    @JsonProperty("authenticatorSelection")
    private AuthenticatorSelection authenticatorSelection;

    @JsonProperty("attestation")
    private String attestation;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("credProtect")
    private String credProtect;

    public RP getRp() {
        return rp;
    }

    public void setRp(RP rp) {
        this.rp = rp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthenticatorSelection getAuthenticatorSelection() {
        return authenticatorSelection;
    }

    public void setAuthenticatorSelection(AuthenticatorSelection authenticatorSelection) {
        this.authenticatorSelection = authenticatorSelection;
    }

    public String getAttestation() {
        return attestation;
    }

    public void setAttestation(String attestation) {
        this.attestation = attestation;
    }

    public String getCredProtect() {
        return credProtect;
    }

    public void setCredProtect(String credProtect) {
        this.credProtect = credProtect;
    }
}
