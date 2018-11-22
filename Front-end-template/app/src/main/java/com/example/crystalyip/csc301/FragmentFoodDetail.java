package com.example.crystalyip.csc301;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.crystalyip.csc301.HTTPInteractions.OrderPlacer;
import com.example.crystalyip.csc301.Model.Order;
import com.example.crystalyip.csc301.Model.StaticStorage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class FragmentFoodDetail extends Fragment  implements View.OnClickListener{
    PopupWindow popUp;
    View popupView;
    View rootView;
    Order orderToPlace;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_food_detail, container, false);
        Bundle bundle = this.getArguments();

        popUp=new PopupWindow(getActivity());
        popupView = inflater.inflate(R.layout.popup_message, null);


        if (bundle != null) {
            int imageID = bundle.getInt("imageURL");
            ImageView imageView=(ImageView) rootView.findViewById(R.id.image_food);
            imageView.setImageResource(imageID);

            TextView tv = (TextView) rootView.findViewById(R.id.food_description);
            String foodDescription = bundle.getString("Description");
            String foodName = bundle.getString("foodName");
            String foodLocation = bundle.getString("foodLocation");
            SpannableString descriptions = new SpannableString(foodDescription);
            descriptions.setSpan(new RelativeSizeSpan(1.3f), 0, foodDescription.indexOf("\n"), 0);
            tv.setText(descriptions);
            orderToPlace = new Order("Pending", StaticStorage.getUserId(), 0, foodName, foodLocation);

        }

        FloatingActionButton order=rootView.findViewById(R.id.btnCart);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrderNotification();
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.setFocusable(true);
                TextView message = popupView.findViewById(R.id.order_message);
                try {
                    message.setText(getSuccessResponse(orderToPlace));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // If you need the PopupWindow to dismiss when when touched outside
                popupWindow.setBackgroundDrawable(new ColorDrawable());

                // Using location, the PopupWindow will be displayed right under anchorView
                popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
            }
        });
        return rootView;
    }

    private void sendOrderNotification(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String sendNotification = "";

                    //This is a Simple Logic to Send Notification different Device Programmatically....
                    if (MainActivity.userId.equals("12")) { //TODO: Define user type so you know what kind of notification to send
                        sendNotification = "12";
                    }
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

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + sendNotification + "\"}],"

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
        });
    }

    private String getSuccessResponse(Order orderToPlace) throws IOException {
        OrderPlacer orderPlacer=new OrderPlacer(orderToPlace);
        return orderPlacer.makeOrder();
    }


    @Override
    public void onClick(View v) {
    }
}
