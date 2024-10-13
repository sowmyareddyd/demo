package com.fido.demo.controller.pojo.registration.options;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticatorSelection {
    @JsonProperty("authenticatorAttachment")
    public String authenticatorAttachment;
    @JsonProperty("requireResidentKey")
    public boolean requireResidentKey;
    @JsonProperty("userVerification")
    public String userVerification;

    public AuthenticatorSelection() {
    }
}