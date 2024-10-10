package com.fido.demo.service;
import com.fido.demo.service.pojo.registration.RegOptionsRequest;
import com.fido.demo.service.pojo.registration.RegOptionsResponse;
import com.fido.demo.service.pojo.registration.RegRequest;
import com.fido.demo.service.pojo.registration.RegResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/fido2")
class RegistrationService {
    
    // Get challenge options for registration
    @GetMapping("/registration/options")
    public RegOptionsResponse getRegOptions(RegOptionsRequest request) {
        return null;
    }

    @PostMapping("/registration")
    public RegResponse createRegistration(RegRequest request){
        return null;
    }
}