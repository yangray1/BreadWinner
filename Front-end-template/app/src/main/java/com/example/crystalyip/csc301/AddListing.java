package com.example.crystalyip.csc301;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;

public class AddListing extends Fragment implements View.OnClickListener{
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    @Override
    public void onClick(View v) {
 
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.add_listing, container, false);
        Button button = view.findViewById(R.id.upload_image);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        filePath = data.getData();
        try {
            bitmap = MediaStore.Images.Media.getBitmap( this.getActivity().getContentResolver(), filePath);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    HTTPRequests.postHTTPImage("http://18.234.123.109/api/test", toByteArray(bitmap), 38);
                }
            });
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
}
