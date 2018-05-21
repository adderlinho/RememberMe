package com.asoftwaresolution.rememberme.restApi.model;

import com.asoftwaresolution.rememberme.restApi.pojo.ReminderPojo;

import java.util.ArrayList;

/**
 * Created by AdderlyS on 20/04/2018.
 */

public class ReminderResponse {
    ArrayList<ReminderPojo> reminderPojos;

    public ArrayList<ReminderPojo> getReminderPojos() {
        return reminderPojos;
    }

    public void setReminderPojos(ArrayList<ReminderPojo> reminderPojos) {
        this.reminderPojos = reminderPojos;
    }
}
