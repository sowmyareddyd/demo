package com.fido.demo.service.manager;

import com.fido.demo.data.repository.RPRepository;
import com.fido.demo.service.pojo.registration.RegOptionsRequest;
import com.fido.demo.service.pojo.registration.RegOptionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationManager {

    @Autowired
    RPRepository rpRepository;


    public RegOptionsResponse getRegOptions(RegOptionsRequest request){
        RegOptionsResponse response = new RegOptionsResponse();

        //System.out.println("RP : "+request.getRp().getId());
        //System.out.println("RP : "+request.getRp());
        // Construct the
        return  null;
    }
}
