package com.company;

import org.json.simple.JSONObject;

public class Main {

    public static void main(String[] args) {
	    JSONObject reseponse = RequestBuilder.buildPlayerLoginRequest("user", "pass").getResponse();
        System.out.println(reseponse.toJSONString());
    }
}
