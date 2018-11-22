package com.example.crystalyip.csc301.HTTPInteractions;

import android.content.res.Resources;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HTTPRequests {
    /**
     * subroutine for recieving and checking the responses of an http request
     * @param con
     * @return
     */
    private static String  getHttpResult(HttpURLConnection con) throws IOException {
        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();
            return sb.toString();
        } else {
            return con.getResponseMessage();
        }
    }



    /**
     * Sends a GET request to the url at urlToRead, and return the string representing the response.
     *
     * @param urlToRead url to GET from
     * @return String response of GET request
     */
    public static String getHTTP(String urlToRead) throws Exception {
        URL url = new URL(urlToRead);
        HttpURLConnection con =  (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setRequestMethod("GET");


        return getHttpResult(con);
    }

    /**
     * Sends a POST request with json data
     * @param urlToRead where to send post to
     * @param jsonToPost the json to send
     * @return string with http response
     * @throws IOException
     */
    public static String postHTTPJson(String urlToRead, JSONObject jsonToPost) throws IOException {
        URL url = new URL(urlToRead);
        HttpURLConnection con =  (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(jsonToPost.toString());
        wr.flush();

        //display what returns the POST request
        return getHttpResult(con);


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
        URL url = new URL(urlToRead);
        HttpURLConnection con =  (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");


        return getHttpResult(con);
    }

}
