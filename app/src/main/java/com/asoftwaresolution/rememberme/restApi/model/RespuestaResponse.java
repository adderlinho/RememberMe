package com.asoftwaresolution.rememberme.restApi.model;

import com.asoftwaresolution.rememberme.restApi.pojo.RespuestaPojo;

import java.util.ArrayList;

/**
 * Created by AdderlyS on 20/04/2018.
 */

public class RespuestaResponse {
    ArrayList<RespuestaPojo> respuestaPojos;

    public ArrayList<RespuestaPojo> getRespuestaPojos() {
        return respuestaPojos;
    }

    public void setRespuestaPojos(ArrayList<RespuestaPojo> respuestaPojos) {
        this.respuestaPojos = respuestaPojos;
    }
}
