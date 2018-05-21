package com.asoftwaresolution.rememberme.restApi;

import com.asoftwaresolution.rememberme.restApi.model.ClientesResponse;
import com.asoftwaresolution.rememberme.restApi.model.FriendResponse;
import com.asoftwaresolution.rememberme.restApi.model.ReminderResponse;
import com.asoftwaresolution.rememberme.restApi.model.RespuestaResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by AdderlyS on 25/10/2017.
 */

public interface EndpointsApi {

    @GET(ConstantesRestApi.JSON_VERIFICATION)
    Call<ClientesResponse> getDataRegistro(@Path("email") String email,
                                           @Path("token_session") String token_session);

    @FormUrlEncoded
    @POST(ConstantesRestApi.JSON_REGISTRO)
    Call<ClientesResponse> setDataRegistro(@Field("id_dispositivo") String id_dispositivo,
                                           @Field("email") String email,
                                           @Field("usuario") String usuario,
                                           @Field("contraseña") String contrasena,
                                           @Field("firebase_id") String firebase_id);

    @FormUrlEncoded
    @POST(ConstantesRestApi.JSON_POST_REMINDERS)
    Call<ReminderResponse> setDataReminder(@Field("id_dispositivo") String id_dispositivo,
                                           @Field("email") String email,
                                           @Field("titulo") String titulo,
                                           @Field("mensaje") String mensaje,
                                           @Field("fecha") String fecha,
                                           @Field("hora") String hora,
                                           @Field("tipo") String tipo);

    @GET(ConstantesRestApi.JSON_GET_REMINDERS)
    Call<ReminderResponse> getDataReminders(@Path("email") String email);

    @FormUrlEncoded
    @POST(ConstantesRestApi.JSON_DELETE_REMINDER)
    Call<RespuestaResponse> deleteDataReminder(@Field("id") String id);


    @FormUrlEncoded
    @POST(ConstantesRestApi.JSON_POST_FRIENDS)
    Call<RespuestaResponse> setDataFriend(@Field("token_friend") String token_friend,
                                          @Field("token_session") String token_session);

    @GET(ConstantesRestApi.JSON_GET_FRIENDS)
    Call<FriendResponse> getDataFriends(@Path("email") String email,
                                        @Path("token_session") String token_session);

    @FormUrlEncoded
    @POST(ConstantesRestApi.JSON_DELETE_FRIEND)
    Call<RespuestaResponse> deleteDataFriend(@Field("token_friend") String token_friend,
                                             @Field("token_session") String token_session);

    @FormUrlEncoded
    @POST(ConstantesRestApi.JSON_SEND_REMINDER)
    Call<RespuestaResponse> sendDataReminderFriend(@Field("token_friend") String token_friend,
                                                   @Field("titulo") String titulo,
                                                   @Field("mensaje") String mensaje,
                                                   @Field("fecha") String fecha,
                                                   @Field("hora") String hora,
                                                   @Field("tipo") String tipo,
                                                   @Field("usuario") String usuario,
                                                   @Field("token_session") String token_session);

    @FormUrlEncoded
    @POST(ConstantesRestApi.JSON_SEND_NOTIFICATION)
    Call<RespuestaResponse> sendDataNotification(@Field("token_friend") String token_friend,
                                                 @Field("token_session") String token_session,
                                                 @Field("mensaje") String mensaje);
}
