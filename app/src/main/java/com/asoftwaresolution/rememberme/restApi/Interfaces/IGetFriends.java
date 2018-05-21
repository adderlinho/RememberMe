package com.asoftwaresolution.rememberme.restApi.Interfaces;

import com.asoftwaresolution.rememberme.adapters.GetFriendsAdapter;
import com.asoftwaresolution.rememberme.adapters.GetRemindersAdapter;
import com.asoftwaresolution.rememberme.restApi.pojo.FriendPojo;
import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;

import java.util.ArrayList;

public interface IGetFriends {
    void generarLinearLayoutVertical();

    GetFriendsAdapter crearAdaptador(ArrayList<FriendPojo> friendPojos);

    void inicializarAdaptadorML(GetFriendsAdapter adaptador);
}
