package com.server;

import org.apache.commons.codec.digest.Crypt;
import org.json.JSONObject;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

public class MessageDatabase {
    private Connection dbConnection = null;
    private static MessageDatabase dbInstance = null;
    private SecureRandom sr = new SecureRandom();
    private int id;

    public static synchronized MessageDatabase getInstance() {
        if (dbInstance == null) {
            dbInstance = new MessageDatabase();
        }
        return dbInstance;
    }

    MessageDatabase() {
    }

    private void init(String dbName) throws SQLException {
        String jdbc = "jdbc:sqlite:" + dbName;
        dbConnection = DriverManager.getConnection(jdbc);
        if (dbConnection != null) {
            String sqlCreateUsers = "create table users (username varchar(50) NOT NULL, password varchar(50) NOT NULL, email varchar(50), primary key(username));";
            Statement initStatement = dbConnection.createStatement();
            initStatement.executeUpdate(sqlCreateUsers);
            initStatement.close();

            Statement msgStatement = dbConnection.createStatement();
            String sqlCreateMessages = "create table messages (id integer NOT NULL, nickname varchar(50) NOT NULL, dangertype varchar(50) NOT NULL, latitude double NOT NULL, longitude double NOT NULL, sent integer NOT NULL, areacode varchar(15), phonenumber varchar(15), weather varchar(15), primary key(id));";
            msgStatement.executeUpdate(sqlCreateMessages);
            msgStatement.close();
        }
    }

    public void open(String dbName) throws SQLException {
        File file = new File(dbName);
        if (file.exists()) {
            String jdbc = "jdbc:sqlite:" + dbName;
            dbConnection = DriverManager.getConnection(jdbc);
        } else {
            init(dbName);
        }
        id = count();
    }

    public void close() throws SQLException {
        if (dbConnection != null) {
            dbConnection.close();
            dbConnection = null;
        }
    }

    public boolean setUser(User user) throws SQLException {
        if (checkUser(user.getUsername())) {
            return false;
        }
        byte[] bytes = new byte[13];
        sr.nextBytes(bytes);
        String saltBytes = new String(Base64.getEncoder().encode(bytes));
        String salt = "$6$" + saltBytes;
        String hashedPass = Crypt.crypt(user.getPassword(), salt);
        String setUser = "insert into users " +
                "VALUES('" + user.getUsername() + "','"
                + hashedPass + "','"
                + user.getEmail() + "')";
        Statement userStatement;
        userStatement = dbConnection.createStatement();
        userStatement.executeUpdate(setUser);
        userStatement.close();
        return true;
    }

    public boolean checkUser(String username) {
        try {
            Statement query;
            ResultSet rs;
            String checkUser = "select username from users where username = '" + username + "'";
            query = dbConnection.createStatement();
            rs = query.executeQuery(checkUser);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String uName, String pWord) throws SQLException {
        try {
            Statement query;
            ResultSet rs;
            String getAuth = "select password from users where username = '" + uName + "'";
            query = dbConnection.createStatement();
            rs = query.executeQuery(getAuth);
            if (!rs.next()) {
                return false;
            } else {
                String hashedPass = rs.getString("password");
                return hashedPass.equals(Crypt.crypt(pWord, hashedPass));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int count() {
        try {
            Statement query;
            ResultSet count;
            String countSql = "select COUNT(*) as total from messages;";
            query = dbConnection.createStatement();
            count = query.executeQuery(countSql);
            if (!count.next()) {
                return 0;
            } else {
                return count.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean insertMsg(JSONObject msg) {

        try {
            Statement query;
            id++;

            String sent = msg.getString("sent");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Date timeDate = df.parse(sent);
            long unixTimestamp = timeDate.getTime();
            System.out.println(unixTimestamp);
            String pNumber = null;
            String aCode = null;
            String weatherString = null;
            if (msg.has("areacode")) {
                aCode = msg.getString("areacode");
            }
            if (msg.has("phonenumber")) {
                pNumber = msg.getString("phonenumber");
            }
            if (msg.has("weather")) {
                weatherString = msg.getString("weather");
            }

            String insertSql = "insert into messages " +
                    "VALUES(" + id + ",'" + msg.getString("nickname") + "','"
                    + msg.getString("dangertype") + "',"
                    + msg.getDouble("latitude") + ","
                    + msg.getDouble("longitude") + ","
                    + unixTimestamp + ",'"
                    + aCode  + "','"
                    + pNumber + "','"
                    + weatherString +
                    "');";

            query = dbConnection.createStatement();
            query.executeUpdate(insertSql);
            query.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<WarningMessage> getMessagesFromTime(long startTime, long endTime) throws SQLException {
        Statement query;
        ArrayList<WarningMessage> userMessages = new ArrayList<>();
        ResultSet rs;
        String querySql = "select * from messages;";
        query = dbConnection.createStatement();
        rs = query.executeQuery(querySql);
        while (rs.next()) {
            long unixTime = rs.getLong("sent");
            if (unixTime >= startTime && unixTime <= endTime) {
                ZonedDateTime timestamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(unixTime), ZoneId.of("UTC"));
                userMessages.add(new WarningMessage(
                                rs.getString("nickname"),
                                rs.getDouble("latitude"),
                                rs.getDouble("longitude"),
                                rs.getString("dangertype"),
                                timestamp,
                                rs.getString("areacode"),
                                rs.getString("phonenumber"),
                                rs.getString("weather")
                        )
                );
            }
        }
        query.close();
        return userMessages;
    }

    public ArrayList<WarningMessage> getMessagesFromUser(String username) throws SQLException {
        Statement query;
        ArrayList<WarningMessage> userMessages = new ArrayList<>();
        ResultSet rs;
        String querySql = "select * from messages where nickname='" +
                username + "';";
        query = dbConnection.createStatement();
        rs = query.executeQuery(querySql);
        while (rs.next()) {
            long unixTime = rs.getLong("sent");
            ZonedDateTime timestamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(unixTime), ZoneId.of("UTC"));
            userMessages.add(new WarningMessage(
                            rs.getString("nickname"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude"),
                            rs.getString("dangertype"),
                            timestamp,
                            rs.getString("areacode"),
                            rs.getString("phonenumber"),
                            rs.getString("weather")
                    )
            );
        }
        query.close();
        return userMessages;
    }

    public ArrayList<WarningMessage> getLocationMessages(Double upLongitude, Double downLongitude, Double upLatitude, Double downLatitude) throws SQLException {
        Statement query;
        ArrayList<WarningMessage> locationMessages = new ArrayList<>();
        ResultSet rs;
        String querySql = "select * from messages;";
        query = dbConnection.createStatement();
        rs = query.executeQuery(querySql);
        while (rs.next()) {
            Double longitude = rs.getDouble("longitude");
            Double latitude = rs.getDouble("latitude");
            System.out.println("long " + downLongitude + " " + longitude + " " + upLongitude);
            System.out.println("lat " + downLatitude + " " + latitude + " " + upLatitude);
            if ((downLongitude <= longitude) && (longitude <= upLongitude) && (downLatitude <= latitude) && (latitude <= upLatitude)) {
                System.out.println("pass");
                long unixTime = rs.getLong("sent");
                ZonedDateTime timestamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(unixTime), ZoneId.of("UTC"));
                locationMessages.add(new WarningMessage(
                                rs.getString("nickname"),
                                rs.getDouble("latitude"),
                                rs.getDouble("longitude"),
                                rs.getString("dangertype"),
                                timestamp,
                                rs.getString("areacode"),
                                rs.getString("phonenumber"),
                                rs.getString("weather")
                        )
                );
            }
        }
        return locationMessages;
    }

    public ArrayList<WarningMessage> getMessages() throws SQLException {
        Statement query;
        ArrayList<WarningMessage> messages = new ArrayList<>();
        ResultSet rs;
        String insertSql = "select * from messages;";

        query = dbConnection.createStatement();
        rs = query.executeQuery(insertSql);
        while (rs.next()) {
            long unixTime = rs.getLong("sent");
            ZonedDateTime timestamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(unixTime), ZoneId.of("UTC"));
            messages.add(new WarningMessage(
                    rs.getString("nickname"),
                    rs.getDouble("latitude"),
                    rs.getDouble("longitude"),
                    rs.getString("dangertype"),
                    timestamp,
                    rs.getString("areacode"),
                    rs.getString("phonenumber"),
                    rs.getString("weather")
                    )
            );
        }
        query.close();
        return messages;
    }
}
