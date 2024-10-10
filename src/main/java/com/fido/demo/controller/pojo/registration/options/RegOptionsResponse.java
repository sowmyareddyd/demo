
package com.fido.demo.controller.pojo.registration.options;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fido.demo.controller.pojo.registration.RP;
import com.fido.demo.controller.pojo.registration.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data // for getters and setters
@NoArgsConstructor
public class RegOptionsResponse {

    @JsonProperty("serverResponse")
    private ServerResponse serverResponse;

    @JsonProperty("rp")
    private RP rp;

    @JsonProperty("user")
    private User user;

    @JsonProperty("challenge")
    private String challenge;

    @JsonProperty("pubKeyCredParams")
    private List<PubKeyCredParam> pubKeyCredParams;

    @JsonProperty("timeout")
    private long timeout;

    @JsonProperty("excludeCredentials")
    private List<Object> excludeCredentials;

    @JsonProperty("authenticatorSelection")
    private AuthenticatorSelection authenticatorSelection;

    @JsonProperty("attestation")
    private String attestation;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("extensions")
    private Extensions extensions;

    public ServerResponse getServerResponse() {
        return serverResponse;
    }

    public void setServerResponse(ServerResponse serverResponse) {
        this.serverResponse = serverResponse;
    }

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

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public List<PubKeyCredParam> getPubKeyCredParams() {
        return pubKeyCredParams;
    }

    public void setPubKeyCredParams(List<PubKeyCredParam> pubKeyCredParams) {
        this.pubKeyCredParams = pubKeyCredParams;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public List<Object> getExcludeCredentials() {
        return excludeCredentials;
    }

    public void setExcludeCredentials(List<Object> excludeCredentials) {
        this.excludeCredentials = excludeCredentials;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Extensions getExtensions() {
        return extensions;
    }

    public void setExtensions(Extensions extensions) {
        this.extensions = extensions;
    }

    public static class ServerResponse {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("description")
        private String description;

        @JsonProperty("internalError")
        private String internalError;

        @JsonProperty("internalErrorCode")
        private int internalErrorCode;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty("internalErrorCodeDescription")
        private String internalErrorCodeDescription;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getInternalError() {
            return internalError;
        }

        public void setInternalError(String internalError) {
            this.internalError = internalError;
        }

        public int getInternalErrorCode() {
            return internalErrorCode;
        }

        public void setInternalErrorCode(int internalErrorCode) {
            this.internalErrorCode = internalErrorCode;
        }

        public String getInternalErrorCodeDescription() {
            return internalErrorCodeDescription;
        }

        public void setInternalErrorCodeDescription(String internalErrorCodeDescription) {
            this.internalErrorCodeDescription = internalErrorCodeDescription;
        }
    }

    public static class PubKeyCredParam {

        @JsonProperty("type")
        private String type;

        @JsonProperty("alg")
        private int alg;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getAlg() {
            return alg;
        }

        public void setAlg(int alg) {
            this.alg = alg;
        }
    }

    public static class Extensions {

        @JsonProperty("credProps")
        private boolean credProps;

        public boolean isCredProps() {
            return credProps;
        }

        public void setCredProps(boolean credProps) {
            this.credProps = credProps;
        }
    }
}
