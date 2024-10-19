package com.fido.demo.controller.pojo.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fido.demo.controller.pojo.registration.ServerPublicKeyCredential;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthnRequest {
    @JsonProperty
    private ServerPublicKeyCredential serverPublicKeyCredential;

    @JsonProperty
    private String sessionId;

    @JsonProperty
    private String origin;

    @JsonProperty
    private String rpId;
}