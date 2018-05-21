package com.asoftwaresolution.rememberme.restApi.pojo;

import java.io.Serializable;

/**
 * Created by AdderlyS on 26/09/2017.
 */
public class ReminderPojo implements Serializable {
    private String id;
    private int alarm_id;
    private String titulo;
    private String mensaje;
    private String fecha;
    private String hora;
    private String tipo;

    public ReminderPojo(String id, int alarm_id, String titulo, String mensaje, String fecha, String hora, String tipo) {
        this.id = id;
        this.alarm_id = alarm_id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
    }

    public ReminderPojo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(int alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
