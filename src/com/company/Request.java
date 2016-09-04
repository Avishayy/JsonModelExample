package com.company;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

/**
 * Created by Avishay on 9/4/2016.
 */
public class Request {
    final static String base_url = "https://127.0.0.1:8080";
    String context_url;
    JSONObject request;

    static { TrustManager[] trustAllCerts = new TrustManager[]{ // void trust manager so we can accept our self-signed certificate
             new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
             }
    };

        try {
            SSLContext sc = SSLContext.getInstance("TLS"); // initialize ssl
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } }

    public Request(String context_url, JSONObject request) {
        this.context_url = context_url;
        this.request = request;
    }


    public JSONObject getResponse() {
        URL url;
        try {
            url = new URL(base_url + context_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);

            byte[] outputInBytes = request.toJSONString().getBytes("UTF-8");
            OutputStream os = con.getOutputStream();
            os.write(outputInBytes);
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JSONParser parser = new JSONParser();
            String input = "";
            String jsonString = "";
            while ((input = br.readLine()) != null) { // I know that JSON is often sent in one line, but better safe than sorry.
                jsonString += input;
            }
            return (JSONObject) parser.parse(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }
}
