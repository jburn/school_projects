package com.server;

public class User {
    private final String username;
    private final String password;
    private final String email;
    public User(String uName, String pWord, String eMail) {
        this.username = uName;
        this.password = pWord;
        this.email = eMail;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

}
