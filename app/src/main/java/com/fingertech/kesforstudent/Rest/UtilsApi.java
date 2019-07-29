package com.fingertech.kesforstudent.Rest;


import com.fingertech.kesforstudent.Controller.Auth;

import static com.fingertech.kesforstudent.Rest.ApiClient.BASE_API;

public class UtilsApi {

    public static Auth getAPIService(){
        return ClientApi.getClient(BASE_API).create(Auth.class);
    }
}
