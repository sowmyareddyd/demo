package com.fido.demo.service.api;
import com.fido.demo.service.pojo.authentication.AuthenticationOptionsResponse;
import com.fido.demo.service.pojo.authentication.AuthenticationRequest;
import com.fido.demo.service.pojo.authentication.AuthenticationResponse;
import com.fido.demo.service.pojo.authentication.AuthenticationOptionsRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fido2")
public interface AuthenticationService {
    
    // Get challenge options for registration
    @PostMapping("/authentication/options")
    public AuthenticationOptionsResponse getRegOptions(AuthenticationOptionsRequest request);

    @PostMapping("/authentication")
    public AuthenticationResponse createRegistration(AuthenticationRequest request);
}