package com.example.crystalyip.csc301.HTTPInteractions;

import android.content.res.Resources;
import android.util.Log;

import com.android.internal.http.multipart.MultipartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

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

    public static String postHTTPImage(String urlToRead, byte[] data, int listId){
        String result="";
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(urlToRead);
        try {
            ByteArrayBody bab = new ByteArrayBody(data, "testing.jpg");
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();

            /* example for setting a HttpMultipartMode */
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", bab);
            httpPost.setEntity(builder.build());
            HttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            } else {
                throw new Resources.NotFoundException();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
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


    public static void sendOneSignalMessage(String userID){
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic NjA1ZTAwZDItMWM2My00YzI3LWI0MTQtY2ZiYzRlNmYyNzEx");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    + "\"app_id\": \"88c285d8-0c80-4ad7-88c6-4d2f590b2915\","

                    + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + userID + "\"}],"

                    + "\"data\": {\"foo\": \"bar\"},"
                    + "\"contents\": {\"en\": \"English Message\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            } else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
