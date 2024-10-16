package com.fido.demo.controller.service.pojo;

import java.math.BigInteger;

import com.fido.demo.controller.pojo.registration.RP;
import com.fido.demo.controller.pojo.registration.options.AuthenticatorSelection;
import com.fido.demo.controller.pojo.registration.User;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class SessionState {
    private String sessionId;

    private RP rp;

    private User user;

    private String challenge;

    private BigInteger rpDbId;

    private BigInteger userDbId;

    private AuthenticatorSelection authenticatorSelection;

    private long timeout;

}
