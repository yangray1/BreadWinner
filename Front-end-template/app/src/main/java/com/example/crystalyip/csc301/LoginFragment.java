package com.example.crystalyip.csc301;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.crystalyip.csc301.HTTPInteractions.HTTPRequests;
import com.example.crystalyip.csc301.Model.StaticStorage;

public class LoginFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_login_screen, container, false);
        Button login_button = view.findViewById(R.id.submit_login);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Login function uses returns a error message when userId and password not in the database
                // returns the userId other wise.

                final String userid = getUserID(view);
                final String password = getPassword(view);
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String res= null;
                        try {
                            res = HTTPRequests.getHTTP("http://18.234.123.109/api/login/"+userid+"/"+password);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("responsemsg"+res);
                        if (res.equals("FAILED")) {
                            // DO SOMETHING
                        } else {
                            System.out.println("responsemsg2"+res);
                            StaticStorage.setUserId(Integer.parseInt(userid));
                            String[] nameDetail=res.split(",");
                            StaticStorage.setFirstName(nameDetail[0]);
                            StaticStorage.setLastName(nameDetail[1]);
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                    new SearchFragment()).commit();
                        }

                    }
                });

            }
        });
        return view;
    }

    public String getUserID(View v){
        EditText userId = v.findViewById(R.id.login_userid);
        return userId.getText().toString();
    }

    public String getPassword(View v){
        EditText password = v.findViewById(R.id.login_password);
        return password.getText().toString();
    }
}
