package com.fingertech.kesforstudent.Rest;


import com.fingertech.kesforstudent.Controller.Auth;

public class UtilsApi {
    private static final String BASE_URL_API = "http://genpin.co.id/ztapi2/";

    public static Auth getAPIService(){
        return ClientApi.getClient(BASE_URL_API).create(Auth.class);
    }
}
