package fr.usmb.farnier.temperapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by clement on 03/03/17.
 */

public class HttpRequests {

    final static private String HTTP_CODE_GET = "GET";
    final static private String HTTP_CODE_POST = "POST";

    /**
     *
     * @param aUrl
     * @param aRequestType
     * @return
     */
    private HttpURLConnection getHttpURLConnection(String aUrl, String aRequestType) {
        try {
            URL url = new URL(aUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(aRequestType);
            con.setRequestProperty("Accept", "application/json");
            return con;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param aCon
     */
    private void checkHttpURLConnectionException(HttpURLConnection aCon) {
        try {
            if (aCon.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + aCon.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param aCon
     */
    private BufferedReader getBufferedReader(HttpURLConnection aCon) {
        try {
            return new BufferedReader(new InputStreamReader((aCon.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void HttpGetRequest(String aUrl) {

        HttpURLConnection con = getHttpURLConnection(aUrl, HTTP_CODE_GET);
        checkHttpURLConnectionException(con);

        BufferedReader br = getBufferedReader(con);

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

            con.disconnect();
    }

    public void HttpPostRequest() {
        try {

            URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"qty\":100,\"name\":\"iPad 4\"}";

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
}
