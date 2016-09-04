package com.company;

import org.json.simple.JSONObject;

/**
 * Created by Avishay on 9/4/2016.
 */
public class RequestBuilder {
    public static Request buildPlayerLoginRequest(String user, String pass) {
        JSONObject request = new JSONObject();
        request.put("username", user);
        request.put("password", pass);
        return new Request("/player/login", request);
    }
}
