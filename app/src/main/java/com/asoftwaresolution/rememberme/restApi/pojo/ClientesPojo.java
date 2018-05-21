package com.asoftwaresolution.rememberme.restApi.pojo;

import java.io.Serializable;

/**
 * Created by AdderlyS on 26/09/2017.
 */
public class ClientesPojo implements Serializable {
    private String id_dispositivo;
    private String email;
    private String usuario;
    private String contrasena;
    private String token_session;
    private String firebase_id;

    public ClientesPojo(String id_dispositivo, String email, String usuario, String contrasena, String token_session, String firebase_id) {
        this.id_dispositivo = id_dispositivo;
        this.email = email;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.token_session = token_session;
        this.firebase_id = firebase_id;
    }

    public ClientesPojo() {
    }

    public String getId_dispositivo() {
        return id_dispositivo;
    }

    public void setId_dispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getToken_session() {
        return token_session;
    }

    public void setToken_session(String token_session) {
        this.token_session = token_session;
    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }
}
