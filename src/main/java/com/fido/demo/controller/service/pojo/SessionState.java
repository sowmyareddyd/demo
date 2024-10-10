package com.fido.demo.controller.service.pojo;

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

    private AuthenticatorSelection authenticatorSelection;

    private long timeout;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
}
