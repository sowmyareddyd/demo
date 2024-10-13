package com.fido.demo.controller.pojo.registration.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fido.demo.controller.pojo.registration.RP;
import com.fido.demo.controller.pojo.registration.User;
import lombok.*;

import java.util.List;


@Data // for getters and setters
@Builder
public class RegOptions {

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

    @JsonProperty("challenge")
    private String challenge;

    @JsonProperty("pubKeyCredParams")
    private List<PubKeyCredParam> pubKeyCredParams;

    @JsonProperty("timeout")
    private long timeout;

    @JsonProperty("excludeCredentials")
    private List<Object> excludeCredentials;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("extensions")
    private Extensions extensions;

    @JsonProperty("serverResponse")
    private ServerResponse serverResponse;

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
