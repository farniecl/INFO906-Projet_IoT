package fr.usmb.farnier.temperapp.rest;

import org.json.JSONObject;

/**
 * Created by clement on 07/03/17.
 */

public interface IHttpRequests {
    /**
     *
     * @param aUrl Request url
     * @return {@link JSONObject}
     */
    JSONObject HttpGetRequest(String aUrl);

    /**
     *
     * @param aUrl Request url
     * @param aInput Request (json format)
     * @return {@link JSONObject}
     */
    JSONObject HttpPostRequest(String aUrl, String aInput);
}
