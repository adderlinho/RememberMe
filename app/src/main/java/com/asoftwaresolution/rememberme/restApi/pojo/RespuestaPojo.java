package com.asoftwaresolution.rememberme.restApi.pojo;

import java.io.Serializable;

/**
 * Created by AdderlyS on 26/09/2017.
 */
public class RespuestaPojo implements Serializable {
    private String respuesta;

    public RespuestaPojo(String respuesta) {
        this.respuesta = respuesta;
    }

    public RespuestaPojo() {
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
