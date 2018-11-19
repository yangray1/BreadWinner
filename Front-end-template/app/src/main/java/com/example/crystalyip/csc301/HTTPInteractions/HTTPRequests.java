package com.example.crystalyip.csc301.HTTPInteractions;

import android.content.res.Resources;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class HTTPRequests {
    /**
     * Sends a GET request to the url at urlToRead, and return the string representing the response.
     *
     * @param urlToRead url to GET from
     * @return String response of GET request
     */
    public static String getHTTP(String urlToRead) throws Exception {
        // reference: https://stackoverflow.com/questions/34691175/how-to-send-httprequest-and-get-json-response-in-android/34691486
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(urlToRead);

        HttpResponse response = httpclient.execute(httpget);

        if (response.getStatusLine().getStatusCode() == 200) {
            String server_response = EntityUtils.toString(response.getEntity());
            return server_response;
        } else {
            System.out.println("no response from server");
        }
        return "";
    }

    /**
     * Sends a POST request with json data
     * @param urlToRead where to send post to
     * @param jsonToPost the json to send
     * @return string with http response
     * @throws IOException
     */
    public static String postHTTPJson(String urlToRead, JSONObject jsonToPost) throws IOException {
        //reference: https://stackoverflow.com/a/19912858
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlToRead);

        httpPost.addHeader("content-type", "application/json");
        StringEntity params = new StringEntity(jsonToPost.toString());
        httpPost.setEntity(params);
        HttpResponse response = httpclient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == 200) {
            String server_response = EntityUtils.toString(response.getEntity());
            return server_response;
        } else {
            throw new Resources.NotFoundException();
        }
    }

    /**
     * Format the string returned from a GET request to our API, ridding it of newline characters
     * and redundant backslashes.
     *
     * @param apiString (JSON formatted) string to format
     * @return correctly formatted string (that can be parsed with a JSON parser)
     */
    public static String formatJSONStringFromResponse(String apiString) {
        String remove_new_line = apiString.replace("\\n", "\\");
        String remove_begin_slash = remove_new_line.replace("\"{", "{");
        String remove_end_slash = remove_begin_slash.replace("}\"", "}");
        String remove_extra_slashes = remove_end_slash.replace("\\", "");
        return remove_extra_slashes;
    }

    /**
     * Sends a POST request
     * @param urlToRead where to send post to
     * @return string with http response
     * @throws IOException
     */
    public static String postHTTP(String urlToRead) throws IOException {
        //reference: https://stackoverflow.com/a/19912858
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlToRead);

        HttpResponse response = httpclient.execute(httpPost);

        if (response.getStatusLine().getStatusCode() == 200) {
            String server_response = EntityUtils.toString(response.getEntity());
            return server_response;
        } else {
            System.out.println("no response from server");
        }
        return "";
    }

}
