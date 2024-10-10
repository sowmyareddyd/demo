package com.fido.demo.controller.api.impl;

import com.fido.demo.controller.api.RegistrationController;
import com.fido.demo.controller.pojo.registration.options.RegOptionsRequest;
import com.fido.demo.controller.pojo.registration.options.RegOptionsResponse;
import com.fido.demo.controller.pojo.registration.RegRequest;
import com.fido.demo.controller.pojo.registration.RegResponse;
import com.fido.demo.controller.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;



@Component("registartionController")
public class RegistartionControllerImpl implements RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @Override
    public ResponseEntity<RegOptionsResponse> getRegOptions(RegOptionsRequest request) {
        RegOptionsResponse regOptionsResponse = registrationService.getRegOptions(request);
        return ResponseEntity.ok(regOptionsResponse);
    }

    @Override
    public ResponseEntity<RegResponse> createRegistration(RegRequest request) {
        RegResponse regResponse = registrationService.getReg(request);
        return ResponseEntity.ok(regResponse);
    }
}