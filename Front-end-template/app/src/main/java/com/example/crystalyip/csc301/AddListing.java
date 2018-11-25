package com.example.crystalyip.csc301;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.preference.DialogPreference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.Listing;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;
import static com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests.postHTTPJson;

public class AddListing extends Fragment implements View.OnClickListener{
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    private View dynamicView;
    @Override
    public void onClick(View v) {
 
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        final View view = inflater.inflate(R.layout.add_listing, container, false);
        View button = view.findViewById(R.id.upload_image);
        dynamicView=view;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);

            }
        });

        // reference: https://www.viralandroid.com/2016/02/android-listview-with-image-and-text.html
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Button submitButton = view.findViewById(R.id.add_listing_button);

        submitButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                String foodName = getFood(view);
                String price = getPrice(view);
                String location = getLocation(view);
                JSONArray tags = getTags(view);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);

                if (checkIfFloat(price)){
                    // send a post request
                    try {
                        JSONObject listing = new JSONObject();
                        listing.put("CookID", 1); // TODO: change the hardcoded UserID value after login is implemented
                        listing.put("Food Name", foodName);
                        listing.put("Price", Float.parseFloat(price));
                        listing.put("Location", location);
                        listing.put("Image", "greatest image 2018");
                        listing.put("tags", tags);

                        postHTTPJson("http://18.234.123.109/api/add", listing);
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                HTTPRequests.postHTTPImage("http://18.234.123.109/api/uploadImage", toByteArray(bitmap), 38);
                            }
                        });
                        builder.setTitle("Successfully added listing.");
                        builder.show();
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        //System.out.println("Error adding listing.");
                    }


                }
                else{
                    // inflate a popup. reference: https://www.youtube.com/watch?v=7vWoi8j5vL4
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    builder.setCancelable(true);
                    builder.setTitle("Invalid parameters");
                    builder.setMessage("Either you're not logged in, or you've supplied an invalid price. Try again.");
                    builder.show();
                }

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        filePath = data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap( this.getActivity().getContentResolver(), filePath);
            ImageView uploaded = dynamicView.findViewById(R.id.upload_image);
            TextView uploadPrompt=dynamicView.findViewById(R.id.upload_message);
            uploadPrompt.setText("");
            uploaded.setImageBitmap(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] toByteArray(Bitmap raw){
        byte[] byteArray = null;

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream ();
            raw.compress (Bitmap.CompressFormat.JPEG, 100, stream);
            byteArray = stream.toByteArray ();
        }
        catch (Exception e) {
            e.printStackTrace ();
        }

        return byteArray;
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this.getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this.getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }

    }

    public /*List<String>*/JSONArray getTags(View view) {
        EditText getTags = view.findViewById(R.id.get_tags);
        String separatedTags = getTags.getText().toString();
        String[] tagsArray = separatedTags.split(" ");

        JSONArray arr = new JSONArray();
        for (int i = 0 ; i < tagsArray.length ; i++){
            arr.put(tagsArray[i]);
        }

        return arr;
    }

    public String getFood(View v){
        EditText getFood = v.findViewById(R.id.get_food_name);
        return getFood.getText().toString();
    }

    public String getPrice(View v){
        EditText getPrice = v.findViewById(R.id.get_price);
        return getPrice.getText().toString();
    }

    public String getLocation(View v){
        EditText getLocation = v.findViewById(R.id.get_location);
        return getLocation.getText().toString();
    }

    public boolean checkIfFloat(String toCheck){
        try{
            Float.parseFloat(toCheck);
        }
        catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
