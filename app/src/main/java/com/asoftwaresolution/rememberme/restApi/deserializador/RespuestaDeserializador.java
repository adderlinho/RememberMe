package com.asoftwaresolution.rememberme.restApi.deserializador;

import com.asoftwaresolution.rememberme.restApi.JsonKeys;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.RespuestaPojo;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by AdderlyS on 26/10/2017.
 */

public class RespuestaDeserializador implements JsonDeserializer<RespuestaResponse> {
    @Override
    public RespuestaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        RespuestaResponse respuestaResponse = gson.fromJson(json, RespuestaResponse.class);
        ArrayList<RespuestaPojo> respuestaPojos = new ArrayList<>();
        JsonObject deleteResponseDataObject  = json.getAsJsonObject();

        String respuesta = deleteResponseDataObject.get(JsonKeys.MEDIA_RESPUESTA).getAsString();

        RespuestaPojo respuestaPojo = new RespuestaPojo();
        respuestaPojo.setRespuesta(respuesta);

        respuestaPojos.add(respuestaPojo);
        respuestaResponse.setRespuestaPojos(respuestaPojos);
        return respuestaResponse;
    }
}
