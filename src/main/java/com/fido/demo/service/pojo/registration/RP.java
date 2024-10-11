package com.fido.demo.service.pojo.registration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RP {
    @JsonProperty("name")
    public String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("icon")
    public String icon;
    @JsonProperty("id")
    public String id;

    public RP() {
    }
}