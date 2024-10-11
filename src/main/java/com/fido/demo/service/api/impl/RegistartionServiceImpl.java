package com.fido.demo.service.api.impl;

import com.fido.demo.service.api.RegistrationService;
import com.fido.demo.service.manager.RegistrationManager;
import com.fido.demo.service.pojo.registration.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component("registartionService")
public class RegistartionServiceImpl implements RegistrationService {

    private static Logger logger = LoggerFactory.getLogger(RegistartionServiceImpl.class);

    @Autowired
    RegistrationManager registrationManager;

    @Override
    public RegOptionsResponse getRegOptions(RegOptionsRequest request) {
        try {
            RegOptionsResponse regOptionsResponse = registrationManager.getRegOptions(request);
        } catch (Exception e){
            logger.error("Exception occurred while getting reg options response", e);
        } finally {
            return 
        }



        return null;
    }

    @Override
    public RegResponse createRegistration(RegRequest request) {
        return null;
    }
}


/*
{
  "rp" : {
    "name" : "Test RP",
    "icon" : null,
    "id" : "localhost"
  },
  "user" : {
    "name" : "TestUser",
    "icon" : null,
    "id" : "65fUCTlqPlOSk22tkrkJ2m8I2MEhpF4fCI_pdosMAzk",
    "displayName" : "Test Display Name"
  },
  "authenticatorSelection": {
    "requireResidentKey": true,
    "userVerification": "preferred",
    "authenticatorAttachment": "platform"
  },
  "attestation": "none",
  "credProtect" : null
}
*/