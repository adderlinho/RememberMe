package com.asoftwaresolution.rememberme.restApi.model;

import com.asoftwaresolution.rememberme.restApi.pojo.FriendPojo;
import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;

import java.util.ArrayList;

/**
 * Created by AdderlyS on 20/04/2018.
 */

public class FriendResponse {
    ArrayList<FriendPojo> friendPojos;

    public ArrayList<FriendPojo> getFriendPojos() {
        return friendPojos;
    }

    public void setFriendPojos(ArrayList<FriendPojo> friendPojos) {
        this.friendPojos = friendPojos;
    }
}
