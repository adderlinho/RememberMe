package com.asoftwaresolution.rememberme.restApi.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import com.asoftwaresolution.rememberme.restApi.JsonKeys;
import com.asoftwaresolution.rememberme.restApi.model.ClientesResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.ClientesPojo;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by AdderlyS on 26/10/2017.
 */

public class ClientesDeserializador implements JsonDeserializer<ClientesResponse> {
    @Override
    public ClientesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        ClientesResponse clientesResponse = gson.fromJson(json, ClientesResponse.class);
        ArrayList<ClientesPojo> clientesPojos = new ArrayList<>();
        JsonObject clientesResponseDataObject  = json.getAsJsonObject();

        String id_dispositivo = "", email = "", usuario = "", contrasena = "", token_session = "", firebase_id = "";

        if(clientesResponseDataObject.has(JsonKeys.MEDIA_ID_DISPOSITIVO))
        {
            id_dispositivo           = clientesResponseDataObject.get(JsonKeys.MEDIA_ID_DISPOSITIVO).getAsString();
        }
        if(clientesResponseDataObject.has(JsonKeys.MEDIA_EMAIL))
        {
            email                    = clientesResponseDataObject.get(JsonKeys.MEDIA_EMAIL).getAsString();
        }
        if(clientesResponseDataObject.has(JsonKeys.MEDIA_USUARIO))
        {
            usuario                  = clientesResponseDataObject.get(JsonKeys.MEDIA_USUARIO).getAsString();
        }
        if(clientesResponseDataObject.has(JsonKeys.MEDIA_CONTRASEÑA))
        {
            contrasena               = clientesResponseDataObject.get(JsonKeys.MEDIA_CONTRASEÑA).getAsString();
        }
        if(clientesResponseDataObject.has(JsonKeys.MEDIA_TOKEN_SESSION))
        {
            token_session            = clientesResponseDataObject.get(JsonKeys.MEDIA_TOKEN_SESSION).getAsString();
        }
        if(clientesResponseDataObject.has(JsonKeys.MEDIA_FIREBASE_ID))
        {
            firebase_id              = clientesResponseDataObject.get(JsonKeys.MEDIA_FIREBASE_ID).getAsString();
        }

        ClientesPojo clientesPojo = new ClientesPojo();
        clientesPojo.setId_dispositivo(id_dispositivo);
        clientesPojo.setEmail(email);
        clientesPojo.setUsuario(usuario);
        clientesPojo.setContrasena(contrasena);
        clientesPojo.setToken_session(token_session);
        clientesPojo.setFirebase_id(firebase_id);

        clientesPojos.add(clientesPojo);
        clientesResponse.setClientesPojos(clientesPojos);
        return clientesResponse;
    }
}
