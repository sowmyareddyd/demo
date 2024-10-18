package com.fido.demo.controller.pojo.authentication;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

@Data
@Builder
public class AuthenticationOptionsResponse {

    @JsonProperty("challenge")
    private String challenge;

    @JsonProperty("timeout")
    private long timeout;

    @JsonProperty("rpId")
    private String rpId;

    @JsonProperty("userVerification")
    String userVerification;

    @JsonProperty("sessionId")
    private String sessionId;

    @JsonProperty("allowCredentials")
    private List<Map<String,String>> allowedCreds;


}


