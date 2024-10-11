package com.fido.demo.service.api;
import com.fido.demo.service.pojo.registration.RegOptionsRequest;
import com.fido.demo.service.pojo.registration.RegOptionsResponse;
import com.fido.demo.service.pojo.registration.RegRequest;
import com.fido.demo.service.pojo.registration.RegResponse;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/fido2")
public interface RegistrationService {
    
    // Get challenge options for registration
    @PostMapping(value = "/registration/options", consumes = "application/json")
    public RegOptionsResponse getRegOptions(@RequestBody RegOptionsRequest request) ;

    @PostMapping("/registration")
    public RegResponse createRegistration(@RequestBody RegRequest request);
}