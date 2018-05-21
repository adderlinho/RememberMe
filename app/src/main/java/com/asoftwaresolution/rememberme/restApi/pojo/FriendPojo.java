package com.asoftwaresolution.rememberme.restApi.pojo;

import java.io.Serializable;

/**
 * Created by AdderlyS on 26/09/2017.
 */
public class FriendPojo implements Serializable {
    private String email;
    private String id;
    private String usuario;
    private String firebase_id;

    public FriendPojo(String email, String id, String usuario, String firebase_id) {
        this.email = email;
        this.id = id;
        this.usuario = usuario;
        this.firebase_id = firebase_id;
    }

    public FriendPojo() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String token_session) {
        this.id = token_session;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }
}
