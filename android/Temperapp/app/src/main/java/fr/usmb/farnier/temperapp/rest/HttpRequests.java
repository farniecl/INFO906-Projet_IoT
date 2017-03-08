package fr.usmb.farnier.temperapp.rest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by clement on 03/03/17.
 */

public class HttpRequests implements IHttpRequests {

    final static private String HTTP_CODE_GET = "GET";
    final static private String HTTP_CODE_POST = "POST";

    /**
     *
     * @param aUrl Request url
     * @param aRequestProperty Request property (Access / Content-Type)
     * @param aRequestType Request type (GET / POST)
     * @return {@link HttpURLConnection}
     */
    private HttpURLConnection getHttpURLConnection(String aUrl, String aRequestProperty, String aRequestType) {
        try {
            URL url = new URL(aUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(aRequestType);
            con.setRequestProperty(aRequestProperty, "application/json");
            return con;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param aCon {@link HttpURLConnection}
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
     * @param aCon {@link HttpURLConnection}
     * @return {@link BufferedReader}
     */
    private BufferedReader getBufferedReader(HttpURLConnection aCon) {
        try {
            return new BufferedReader(new InputStreamReader((aCon.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param aBr {@link BufferedReader}
     * @return {@link JSONObject}
     */
    private JSONObject bufferedReaderToJsonObject(BufferedReader aBr) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = aBr.readLine()) != null) {
                sb.append(line);
            }
            return new JSONObject(sb.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param aJson {@link JSONObject}
     */
    private void checkJsonObject(JSONObject aJson) {
        try {
            if (aJson == null) {
                throw new Exception("La chaine de caractère JSON est null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject HttpGetRequest(String aUrl) {
        HttpURLConnection con = getHttpURLConnection(aUrl, "Accept", HTTP_CODE_GET);
        checkHttpURLConnectionException(con);
        JSONObject json = bufferedReaderToJsonObject(getBufferedReader(con));
        checkJsonObject(json);
        if (con != null) {
            con.disconnect();
        }
        return json;
    }

    @Override
    public JSONObject HttpPostRequest(String aUrl, String aInput) {
        HttpURLConnection con = getHttpURLConnection(aUrl, "Content-Type", HTTP_CODE_POST);
        checkHttpURLConnectionException(con);
        try {
            if (con != null) {
                OutputStream os = con.getOutputStream();
                os.write(aInput.getBytes());
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        checkHttpURLConnectionException(con);
        JSONObject json = bufferedReaderToJsonObject(getBufferedReader(con));
        checkJsonObject(json);
        if (con != null) {
            con.disconnect();
        }
        return json;
    }
}
