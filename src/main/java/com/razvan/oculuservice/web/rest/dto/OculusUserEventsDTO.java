package com.razvan.oculuservice.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.razvan.oculuservice.domain.OculusEvent;
import com.razvan.oculuservice.domain.OculusUser;

import java.util.List;

/**
 * Author: R.B
 * Date: 01.06.2015
 * Time: 19:45
 */
public class OculusUserEventsDTO {
    private OculusUser oculusUser;

    @JsonProperty("oculusEventList")
    private List<OculusEvent> oculusEventList;

    @JsonCreator
    public OculusUserEventsDTO() {}

    public OculusUserEventsDTO(OculusUser oculusUser, List<OculusEvent> oculusEventList) {
        this.oculusUser = oculusUser;
        this.oculusEventList = oculusEventList;
    }

    public void setOculusUser(OculusUser oculusUser) {this.oculusUser = oculusUser;}

    public OculusUser getOculusUser() {return oculusUser;}

    public void setOculusEventList(List<OculusEvent> oculusEventList) {
        this.oculusEventList = oculusEventList;
    }

    public List<OculusEvent> getOculusEventList() {
        return oculusEventList;
    }

    @Override
    public String toString() {
        return "OculusUserEventsDTO{" +
            "oculusUser=" + oculusUser +
            ", oculusEventList=" + oculusEventList +
            '}';
    }

}
