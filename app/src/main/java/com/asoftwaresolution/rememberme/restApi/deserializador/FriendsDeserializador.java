package com.asoftwaresolution.rememberme.restApi.deserializador;

import com.asoftwaresolution.rememberme.restApi.JsonKeys;
import com.asoftwaresolution.rememberme.restApi.model.FriendResponse;
import com.asoftwaresolution.rememberme.restApi.model.ReminderResponse;
import com.asoftwaresolution.rememberme.restApi.pojo.FriendPojo;
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

public class FriendsDeserializador implements JsonDeserializer<FriendResponse> {
    @Override
    public FriendResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        FriendResponse friendResponse = new FriendResponse();

        Type arrayType = new TypeToken<ArrayList<FriendPojo>>(){}.getType();
        ArrayList<FriendPojo> friendPojos = gson.fromJson(json, arrayType);
        friendResponse.setFriendPojos(friendPojos);

        return friendResponse;
    }
}
