package com.fido.demo.controller.api;

import com.fido.demo.controller.pojo.registration.options.RegOptions;
import com.fido.demo.controller.pojo.registration.RegRequest;
import com.fido.demo.controller.pojo.registration.RegResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/fido2")
public interface RegistrationController {
    
    // Get challenge options for registration
    @PostMapping(value = "/registration/options", consumes = "application/json")
    public ResponseEntity<RegOptions> getRegOptions(@RequestBody RegOptions request) ;

    @PostMapping("/registration")
    public ResponseEntity<RegRequest> createRegistration(@RequestBody RegRequest request);
}