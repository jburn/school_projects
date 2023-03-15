package com.server;
import com.sun.net.httpserver.*;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.concurrent.Executors;

public class Server {
    private Server() {
    }
    private static SSLContext serverSSLContext(String keyStore, String pWord) throws Exception {
        char[] passphrase = pWord.toCharArray();
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(Files.newInputStream(Paths.get(keyStore)), passphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passphrase);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        SSLContext ssl = SSLContext.getInstance("TLS");
        ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        return ssl;
    }
    public static void main(String[] args) {
        try {
            HttpsServer server = HttpsServer.create(new InetSocketAddress(8001),0);
            SSLContext sslContext = serverSSLContext(args[0], args[1]);
            MessageDatabase db = MessageDatabase.getInstance();
            db.open("coords.db");
            server.setHttpsConfigurator (new HttpsConfigurator(sslContext) {
                public void configure (HttpsParameters params) {
                    InetSocketAddress remote = params.getClientAddress();
                    SSLContext c = getSSLContext();
                    SSLParameters sslParams = c.getDefaultSSLParameters();
                    params.setSSLParameters(sslParams);
                }
            });
            UserAuthenticator userAuthenticator = new UserAuthenticator("/warning");
            HttpContext warningContext = server.createContext("/warning", new MessageHandler());
            warningContext.setAuthenticator(userAuthenticator);
            HttpContext registerContext = server.createContext("/registration", new RegistrationHandler(userAuthenticator));
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            System.out.println("Server started.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}