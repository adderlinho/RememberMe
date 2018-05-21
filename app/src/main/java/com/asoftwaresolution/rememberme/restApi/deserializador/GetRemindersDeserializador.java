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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by AdderlyS on 26/10/2017.
 */

public class GetRemindersDeserializador implements JsonDeserializer<ReminderResponse> {
    @Override
    public ReminderResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        ReminderResponse reminderResponse = new ReminderResponse();

        Type arrayType = new TypeToken<ArrayList<ReminderPojo>>(){}.getType();
        ArrayList<ReminderPojo> reminderPojos = gson.fromJson(json, arrayType);
        reminderResponse.setReminderPojos(reminderPojos);

        return reminderResponse;
    }
}
