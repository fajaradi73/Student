package com.fingertech.kesforstudent.Rest;


import com.fingertech.kesforstudent.Controller.Auth;

public class UtilsApi {
    private static final String BASE_URL_API = "https://kes.co.id/api2/";

    public static Auth getAPIService(){
        return ClientApi.getClient(BASE_URL_API).create(Auth.class);
    }
}
