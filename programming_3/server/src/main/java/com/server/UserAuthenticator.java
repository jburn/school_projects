package com.server;

import com.sun.net.httpserver.BasicAuthenticator;
import java.sql.SQLException;

public class UserAuthenticator extends BasicAuthenticator {
    public UserAuthenticator(String realm) {
        super(realm);
    }

    @Override
    public boolean checkCredentials(String userName, String passWord) {
        MessageDatabase db = MessageDatabase.getInstance();
        try {
            return db.authenticateUser(userName, passWord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addUser(String userName, String passWord, String eMail) {
        MessageDatabase db = MessageDatabase.getInstance();
        try {
            return db.setUser(new User(userName, passWord, eMail));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
