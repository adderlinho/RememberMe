package com.asoftwaresolution.rememberme.restApi.deserializador;

import com.asoftwaresolution.rememberme.restApi.JsonKeys;
import com.asoftwaresolution.rememberme.restApi.model.ReminderResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;
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

public class ReminderDeserializador implements JsonDeserializer<ReminderResponse> {
    @Override
    public ReminderResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        ReminderResponse reminderOTResponse = gson.fromJson(json, ReminderResponse.class);
        ArrayList<ReminderPojo> reminderOTPojos = new ArrayList<>();
        JsonObject reminderotResponseDataObject  = json.getAsJsonObject();

        String id = reminderotResponseDataObject.get(JsonKeys.MEDIA_TOKEN_REMINDER).getAsString();
        String titulo = reminderotResponseDataObject.get(JsonKeys.MEDIA_TITULO).getAsString();
        String mensaje = reminderotResponseDataObject.get(JsonKeys.MEDIA_MENSAJE).getAsString();
        String fecha = reminderotResponseDataObject.get(JsonKeys.MEDIA_FECHA).getAsString();
        String hora = reminderotResponseDataObject.get(JsonKeys.MEDIA_HORA).getAsString();
        String tipo = reminderotResponseDataObject.get(JsonKeys.MEDIA_TIPO).getAsString();

        ReminderPojo reminderPojo = new ReminderPojo();
        reminderPojo.setId(id);
        reminderPojo.setTitulo(titulo);
        reminderPojo.setMensaje(mensaje);
        reminderPojo.setFecha(fecha);
        reminderPojo.setHora(hora);
        reminderPojo.setTipo(tipo);

        reminderOTPojos.add(reminderPojo);
        reminderOTResponse.setReminderPojos(reminderOTPojos);
        return reminderOTResponse;
    }
}
