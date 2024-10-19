package com.fido.demo.controller.pojo.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
public class RegRequest {

    /*----------------- Request fields (begin) -----------------------------*/
    @JsonProperty("serverPublicKeyCredential")
    private ServerPublicKeyCredential serverPublicKeyCredential;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("rpId")
    private String rpId;

    @JsonProperty("tokenBinding")
    private String tokenBinding;
    /*----------------- Request fields (end) -----------------------------*/

    /*----------------- Response fields (being) -----------------------------*/
    @JsonProperty("aaguid")
    private String aaguid;

    @JsonProperty("credentialId")
    private String credentialId;

    @JsonProperty("attestationType")
    private String attestationType;

    @JsonProperty("authenticatorTransports")
    private List<String> authenticatorTransports;

    @JsonProperty("userVerified")
    private boolean userVerified;

    @JsonProperty("rk") // resident key or not
    private boolean rk;


    // Getters and Setters
    public ServerPublicKeyCredential getServerPublicKeyCredential() {
        return serverPublicKeyCredential;
    }

    public void setServerPublicKeyCredential(ServerPublicKeyCredential serverPublicKeyCredential) {
        this.serverPublicKeyCredential = serverPublicKeyCredential;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public String getTokenBinding() {
        return tokenBinding;
    }

    public void setTokenBinding(String tokenBinding) {
        this.tokenBinding = tokenBinding;
    }

}
