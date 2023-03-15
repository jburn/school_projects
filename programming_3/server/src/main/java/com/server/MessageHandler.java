package com.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class MessageHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange xc) throws IOException {
        // init
        int responseCode;
        String responseBody;
        StringBuilder respBuilder = new StringBuilder();

        // check method to see how to handle request
        try {
            if (xc.getRequestMethod().equalsIgnoreCase("POST")) {
                responseCode = handlePost(xc);
            } else if (xc.getRequestMethod().equalsIgnoreCase("GET")) {
                responseCode = handleGet(xc);
            } else if (xc.getRequestMethod().equalsIgnoreCase("DELETE")){
                respBuilder.append("Not supported");
                responseCode = 400;
            } else {
                respBuilder.append("Not found");
                responseCode = 404;
            }
        } catch (Exception e) {
            respBuilder.append("Internal server error: ").append(e.getMessage());
            responseCode = 500;
        }
        System.out.println(responseCode);
        if (!(299 >= responseCode && responseCode >= 200)) {
            responseBody = respBuilder.toString();
            byte[] respBytes = responseBody.getBytes(StandardCharsets.UTF_8);
            System.out.println("Sending response " + responseCode + " with body: " + responseBody);
            xc.sendResponseHeaders(responseCode, respBytes.length);
            OutputStream outputStream = xc.getResponseBody();
            outputStream.write(respBytes);
            outputStream.close();
        }
    }

    private int handleGet(HttpExchange xc) throws Exception{
        int responseCode = 200; // init return code to 200 OK
        MessageDatabase db = MessageDatabase.getInstance(); // get database instance

        if (db.count() == 0) { // if database has no content return 204 no content
            responseCode = 204;
            xc.sendResponseHeaders(responseCode, -1);
        } else {
            ArrayList<WarningMessage> wMessages = db.getMessages(); // query all messages from database
            // send messages in return
            sendReturnList(xc, wMessages);
        }
        return responseCode;
    }

    private int handlePost(HttpExchange xc) throws Exception {
        WeatherService ws = new WeatherService(); // init weather service
        int returnCode = 200; // init return code to 200 OK

        // All valid danger types into list
        String[] dTypes = {"Deer", "Reindeer", "Moose", "Other"};
        ArrayList<String> validDangers = new ArrayList<>();
        Collections.addAll(validDangers, dTypes);

        // Get body of request
        InputStream bodyStream = xc.getRequestBody();
        String text = new BufferedReader(new InputStreamReader(bodyStream,
                StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        bodyStream.close();
        System.out.println("read: " + text);

        // Make request into a JSON object
        JSONObject messageJSON = new JSONObject(text);

        // check and handle different query types
        if (messageJSON.has("query")) {
            String queryType = messageJSON.getString("query");
            ArrayList<WarningMessage> wmList = new ArrayList<>();
            MessageDatabase db = MessageDatabase.getInstance();
            if (queryType.equalsIgnoreCase("user")) { // query by user
                wmList = db.getMessagesFromUser(messageJSON.getString("nickname"));
            } else if (queryType.equalsIgnoreCase("time")) { // query by time
                String startTimeString = messageJSON.getString("timestart");
                String endTimeString = messageJSON.getString("timeend");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                Date startTimeDate = df.parse(startTimeString);
                Date endTimeDate = df.parse(endTimeString);
                wmList = db.getMessagesFromTime(startTimeDate.getTime(), endTimeDate.getTime());
            } else if (queryType.equalsIgnoreCase("location")) { // query by location
                Double upLong = messageJSON.getDouble("uplongitude");
                Double downLong = messageJSON.getDouble("downlongitude");
                Double upLat = messageJSON.getDouble("uplatitude");
                Double downLat = messageJSON.getDouble("downlatitude");
                System.out.println("upLong" + upLong);
                System.out.println("downlong" + downLong);
                System.out.println("uplat" + upLat);
                System.out.println("downlat" + downLat);

                wmList = db.getLocationMessages(upLong, downLong, upLat, downLat);
            }
            sendReturnList(xc, wmList);
            return 200;
        }

        // Check if weather service query is needed
        if (messageJSON.has("weather")) {
            Double latitude = messageJSON.getDouble("latitude");
            Double longitude = messageJSON.getDouble("longitude");
            String weather = ws.getWeather(latitude, longitude);
            messageJSON.put("weather", weather);
        }

        String dType = messageJSON.getString("dangertype");
        String sent = messageJSON.getString("sent");
        if (sent.length() != "yyyy-MM-ddTHH:mm:ss.SSSX".length()) { // Check that timestamp is correct length
            returnCode = 409;
        } else if (!validDangers.contains(dType)) { // Check that request has a valid danger type
            returnCode = 402;
        } else {
            MessageDatabase db = MessageDatabase.getInstance();
            if (!db.insertMsg(messageJSON)) { // insert JSON object into database
                returnCode = 402;
            }
        }
        xc.sendResponseHeaders(returnCode, -1);
        return returnCode;
    }

    private void sendReturnList(HttpExchange xc, ArrayList<WarningMessage> msgList) throws IOException {
        String resp;
        JSONArray outJSON = new JSONArray();
        for (WarningMessage wm : msgList) {
            JSONObject jsonObj = new JSONObject();
            ZonedDateTime time = wm.getTimestamp();
            jsonObj.put("nickname", wm.getNick());
            jsonObj.put("latitude", wm.getLatitude());
            jsonObj.put("longitude", wm.getLongitude());
            jsonObj.put("dangertype", wm.getDangerType());
            jsonObj.put("sent", DateTimeFormatter.ISO_INSTANT.format(time));

            // check optional message attributes
            if (wm.getAreaCode() != null && !wm.getAreaCode().equalsIgnoreCase("null")) {
                jsonObj.put("areacode", wm.getAreaCode());
            }
            if (wm.getPhoneNumber() != null && !wm.getPhoneNumber().equalsIgnoreCase("null")) {
                jsonObj.put("phonenumber", wm.getPhoneNumber());
            }
            if (wm.getWeather() != null && !wm.getWeather().equalsIgnoreCase("null")) {
                jsonObj.put("weather", Integer.parseInt(wm.getWeather()));
            }
            outJSON.put(jsonObj);
        }
        resp = outJSON.toString();
        System.out.println(resp);
        byte [] bytes = resp.getBytes(StandardCharsets.UTF_8);
        xc.sendResponseHeaders(200, bytes.length);
        OutputStream outputStream = xc.getResponseBody();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }
}
