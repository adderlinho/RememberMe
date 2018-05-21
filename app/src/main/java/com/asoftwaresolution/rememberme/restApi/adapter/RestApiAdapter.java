package com.asoftwaresolution.rememberme.restApi.adapter;

import com.asoftwaresolution.rememberme.restApi.deserializador.FriendsDeserializador;
import com.asoftwaresolution.rememberme.restApi.deserializador.GetRemindersDeserializador;
import com.asoftwaresolution.rememberme.restApi.deserializador.ReminderDeserializador;
import com.asoftwaresolution.rememberme.restApi.deserializador.RespuestaDeserializador;
import com.asoftwaresolution.rememberme.restApi.model.FriendResponse;
import com.asoftwaresolution.rememberme.restApi.model.ReminderResponse;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.asoftwaresolution.rememberme.restApi.ConstantesRestApi;
import com.asoftwaresolution.rememberme.restApi.EndpointsApi;
import com.asoftwaresolution.rememberme.restApi.deserializador.ClientesDeserializador;
import com.asoftwaresolution.rememberme.restApi.model.ClientesResponse;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AdderlyS on 20/04/2018.
 */

public class RestApiAdapter {

    public EndpointsApi establecerConexionRestApiHeroku(Gson gson){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(EndpointsApi.class);
    }

    public Gson construyeGsonDeserializadorClientes(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ClientesResponse.class, new ClientesDeserializador());
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorReminder(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ReminderResponse.class, new ReminderDeserializador());
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorGetReminders(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ReminderResponse.class, new GetRemindersDeserializador());
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorRespuesta(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RespuestaResponse.class, new RespuestaDeserializador());
        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorFriends(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(FriendResponse.class, new FriendsDeserializador());
        return gsonBuilder.create();
    }
}
