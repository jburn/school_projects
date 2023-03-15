package com.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationHandler implements HttpHandler {
    private UserAuthenticator userAuthenticator = null;
    public RegistrationHandler(UserAuthenticator uAuth) {
        userAuthenticator = uAuth;
    }
    @Override
    public void handle(HttpExchange xc) throws IOException {
        if (xc.getRequestMethod().equalsIgnoreCase("POST")) {
            InputStream bodyStream = xc.getRequestBody();
            String text = new BufferedReader(new InputStreamReader(bodyStream,
                    StandardCharsets.UTF_8)).lines().collect(Collectors.joining());
            System.out.println(text);
            int returnCode;
            try {
                JSONObject credentials = new JSONObject(text);
                String uName = credentials.getString("username");
                String pWord = credentials.getString("password");
                String eMail = credentials.getString("email");
                if (uName.length() == 0 || pWord.length() == 0) {
                    returnCode = 403;
                } else {
                    returnCode = userAuthenticator.addUser(uName, pWord, eMail) ? 200 : 403;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                returnCode = 402;
            }
            System.out.println(returnCode);
            xc.sendResponseHeaders(returnCode, -1);
        } else if (xc.getRequestMethod().equalsIgnoreCase("GET")) {
            String errorResp = "Not supported";
            byte[] bytes = errorResp.getBytes();
            xc.sendResponseHeaders(400, bytes.length);
            OutputStream outputStream = xc.getResponseBody();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } else {
            xc.sendResponseHeaders(404, -1);
        }
        xc.close();
    }
}
