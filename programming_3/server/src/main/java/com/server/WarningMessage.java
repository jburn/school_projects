package com.server;

import java.time.ZonedDateTime;

public class WarningMessage {
    private final String nick;
    private final Double latitude;
    private final Double longitude;
    private final String dangerType;
    private final ZonedDateTime timestamp;
    private final String areaCode;
    private final String phoneNumber;

    private final String weather;

    public WarningMessage(String uName, Double lat, Double lon, String danType, ZonedDateTime tStamp, String aCode, String pNumber, String weat) {
        nick = uName;
        latitude = lat;
        longitude = lon;
        dangerType = danType;
        timestamp = tStamp;
        areaCode = aCode;
        phoneNumber = pNumber;
        weather = weat;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDangerType() {
        return dangerType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getNick() {
        return nick;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public String getWeather() {
        return weather;
    }

    public long getDateAsInt() {
        return timestamp.toInstant().toEpochMilli();
    }
}
