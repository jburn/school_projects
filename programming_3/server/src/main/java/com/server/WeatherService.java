package com.server;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.stream.Collectors;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class WeatherService {

    private String url;
    private HttpClient client;
    public WeatherService() {}
    public String getWeather(Double latitude, Double longitude) throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        // Open HTTPS connection to weather service
        //initWeather("https://localhost:4001/weather");
        // Open HTTP connection to weather service
        String url = "http://localhost:4001/weather";
        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

        // Prepare XML string and request properties
        String xmlString =
                "<coordinates>" +
                    "<latitude>" +
                        latitude.toString() +
                    "</latitude>" +
                    "<longitude>" +
                        longitude.toString() +
                    "</longitude>" +
                "</coordinates>"
        ;
        byte[] bytes = xmlString.getBytes(StandardCharsets.UTF_8);


        // Send request and close output stream
        /*
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/xml")
                .POST(HttpRequest.BodyPublishers.ofByteArray(bytes))
                .build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());*/
        // HTTP alternative, commented out to for HTTPS functionality

        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();

        System.out.println("sent to weather");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // check for valid response
            // read response
            String respXml = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                            StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            System.out.println(respXml);

            // parse response into a valid xml object to query
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(respXml));
            Document xml = builder.parse(is);

            // get weather info from xml
            Element rootElement = xml.getDocumentElement();
            //String unit = rootElement.getElementsByTagName("Unit").item(0).getTextContent();
            return rootElement.getElementsByTagName("temperature").item(0).getTextContent();
        }
        return "Weather service error";
    }

    // method for HTTPS weather service
    private void initWeather(String url) {
        TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{tm}, null);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        this.url = url;
        this.client = HttpClient.newBuilder().sslContext(sslContext).build();
    }
}
