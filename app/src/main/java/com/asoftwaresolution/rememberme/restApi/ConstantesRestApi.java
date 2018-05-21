package com.asoftwaresolution.rememberme.restApi;

/**
 * Created by AdderlyS on 25/10/2017.
 */

public class ConstantesRestApi {
    public static final String ROOT_URL_SERVER        = "https://agile-spire-74809.herokuapp.com/";
    public static final String JSON_VERIFICATION      = "/verificar-token/{email}/{token_session}";
    public static final String JSON_REGISTRO          = "registrar-usuario";
    public static final String JSON_POST_REMINDERS    = "registrar-reminder";
    public static final String JSON_GET_REMINDERS     = "/obtener-reminders/{email}";
    public static final String JSON_DELETE_REMINDER   = "borrar-reminder";
    public static final String JSON_POST_FRIENDS      = "registrar-friend";
    public static final String JSON_GET_FRIENDS       = "/obtener-friends/{email}/{token_session}";
    public static final String JSON_DELETE_FRIEND     = "borrar-friend";
    public static final String JSON_SEND_REMINDER     = "enviar-reminder";
    public static final String JSON_SEND_NOTIFICATION = "enviar-notificacion";
}
