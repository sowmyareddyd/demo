package com.fido.demo.util;

import com.fido.demo.data.entity.RelyingPartyConfigEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.fido.demo.controller.pojo.registration.options.RegOptionsResponse.PubKeyCredParam;

@Component
public class RpUtils {

    public List<PubKeyCredParam> getPubKeyCredParam(List<RelyingPartyConfigEntity> rpConfigs) {
        RelyingPartyConfigEntity algorithms = rpConfigs.stream()
                                                    .filter(rpConfig -> rpConfig.getSettingKey().equals("public_key_alg"))
                                                    .findFirst()
                .orElse(null);

        if (algorithms == null) {
            throw new RuntimeException("No Algorithms found in RelyingPartyConfig");
        }

        String alg = algorithms.getSettingValue();

        String[] tokens = alg.split(",");
        List<PubKeyCredParam> pubKeyCredParam = new ArrayList<PubKeyCredParam>();

        for(int i=0; i<tokens.length; i++) {
            PubKeyCredParam p = new PubKeyCredParam();
            p.setType("public-key");
            p.setAlg(Integer.parseInt(tokens[i]));
            pubKeyCredParam.add(p);
        }

        return pubKeyCredParam;
    }

    public long getTimeout(List<RelyingPartyConfigEntity> rpConfigs){
        RelyingPartyConfigEntity timeout = rpConfigs.stream()
                                                    .filter(rpConfig -> rpConfig.getSettingKey().equals("timeout"))
                                                    .findFirst().orElse(null);

        if(timeout == null){
            return 180000L;
        }

        String value = timeout.getSettingValue();
        return Long.parseLong(value);

    }

    public String getAttestation(List<RelyingPartyConfigEntity> rpConfigs){
        RelyingPartyConfigEntity attestation = rpConfigs.stream()
                                                    .filter(rpConfig -> rpConfig.getSettingKey().equals("attestation"))
                                                    .findFirst().orElse(null);
        if(attestation == null){
            return "none";
        }
        return attestation.getSettingValue();
    }
}
