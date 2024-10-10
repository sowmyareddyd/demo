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

    // Inner classes for nested structures

    public static class ServerPublicKeyCredential {

        @JsonProperty("id")
        private String id;

        @JsonProperty("type")
        private String type;

        @JsonProperty("response")
        private Response response;

        @JsonProperty("extensions")
        private Extensions extensions;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Response getResponse() {
            return response;
        }

        public void setResponse(Response response) {
            this.response = response;
        }

        public Extensions getExtensions() {
            return extensions;
        }

        public void setExtensions(Extensions extensions) {
            this.extensions = extensions;
        }
    }

    public static class Response {

        @JsonProperty("clientDataJSON")
        private String clientDataJSON;

        @JsonProperty("attestationObject")
        private String attestationObject;

        @Getter
        @Setter
        @JsonProperty("authenticatorData")
        private String authenticatorData;

        @Getter
        @Setter
        @JsonProperty("publicKey")
        private String publicKey;

        @JsonProperty("transports")
        private List<String> transports;

        // Getters and Setters
        public String getClientDataJSON() {
            return clientDataJSON;
        }

        public void setClientDataJSON(String clientDataJSON) {
            this.clientDataJSON = clientDataJSON;
        }

        public String getAttestationObject() {
            return attestationObject;
        }

        public void setAttestationObject(String attestationObject) {
            this.attestationObject = attestationObject;
        }

        public List<String> getTransports() {
            return transports;
        }

        public void setTransports(List<String> transports) {
            this.transports = transports;
        }
    }

    public static class Extensions {

        @JsonProperty("appid")
        private String appid;

        @JsonProperty("txAuthSimple")
        private String txAuthSimple;

        @JsonProperty("txAuthGeneric")
        private String txAuthGeneric;

        @JsonProperty("authnSel")
        private String authnSel;

        @JsonProperty("exts")
        private String exts;

        @JsonProperty("uvi")
        private String uvi;

        @JsonProperty("loc")
        private String loc;

        @JsonProperty("biometricPerfBounds")
        private String biometricPerfBounds;

        @JsonProperty("credProps")
        private CredProps credProps;

        // Getters and Setters
        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTxAuthSimple() {
            return txAuthSimple;
        }

        public void setTxAuthSimple(String txAuthSimple) {
            this.txAuthSimple = txAuthSimple;
        }

        public String getTxAuthGeneric() {
            return txAuthGeneric;
        }

        public void setTxAuthGeneric(String txAuthGeneric) {
            this.txAuthGeneric = txAuthGeneric;
        }

        public String getAuthnSel() {
            return authnSel;
        }

        public void setAuthnSel(String authnSel) {
            this.authnSel = authnSel;
        }

        public String getExts() {
            return exts;
        }

        public void setExts(String exts) {
            this.exts = exts;
        }

        public String getUvi() {
            return uvi;
        }

        public void setUvi(String uvi) {
            this.uvi = uvi;
        }

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getBiometricPerfBounds() {
            return biometricPerfBounds;
        }

        public void setBiometricPerfBounds(String biometricPerfBounds) {
            this.biometricPerfBounds = biometricPerfBounds;
        }

        public CredProps getCredProps() {
            return credProps;
        }

        public void setCredProps(CredProps credProps) {
            this.credProps = credProps;
        }
    }

    public static class CredProps {

        @JsonProperty("rk")
        private boolean rk;

        // Getters and Setters
        public boolean isRk() {
            return rk;
        }

        public void setRk(boolean rk) {
            this.rk = rk;
        }
    }
}
