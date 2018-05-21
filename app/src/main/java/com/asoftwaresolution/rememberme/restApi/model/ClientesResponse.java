package com.asoftwaresolution.rememberme.restApi.model;

import com.asoftwaresolution.rememberme.restApi.pojo.ClientesPojo;

import java.util.ArrayList;

/**
 * Created by AdderlyS on 20/04/2018.
 */

public class ClientesResponse {
    ArrayList<ClientesPojo> clientesPojos;

    public ArrayList<ClientesPojo> getClientesPojos() {
        return clientesPojos;
    }

    public void setClientesPojos(ArrayList<ClientesPojo> clientesPojos) {
        this.clientesPojos = clientesPojos;
    }
}
