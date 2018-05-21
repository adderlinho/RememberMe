package com.asoftwaresolution.rememberme.restApi.Interfaces;

import com.asoftwaresolution.rememberme.adapters.GetRemindersAdapter;
import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;

import java.util.ArrayList;

public interface IGetReminders {
    void generarLinearLayoutVertical();

    GetRemindersAdapter crearAdaptador(ArrayList<ReminderPojo> reminderPojos);

    void inicializarAdaptadorML(GetRemindersAdapter adaptador);
}
