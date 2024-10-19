package com.fido.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JSONUtils<T> {

    String toJSONString(T object){
        ObjectMapper mapper = new ObjectMapper();
        String ret = "";
        try {
            ret = mapper.writeValueAsString(object);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
        return ret;
    }
}
