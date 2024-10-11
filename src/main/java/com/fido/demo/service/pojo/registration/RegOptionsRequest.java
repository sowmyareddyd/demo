package com.fido.demo.service.pojo.registration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data // for getters and setters
@NoArgsConstructor
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
    
}
