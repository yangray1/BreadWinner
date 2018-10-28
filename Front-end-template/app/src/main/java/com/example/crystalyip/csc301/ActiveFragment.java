package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ActiveFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_active_orders, container, false);
        // link the TextView containing the address to the google maps by creating an onClick action
        TextView directions = (TextView) rootView.findViewById(R.id.location);
        directions.setOnClickListener(this);
        return rootView;
    }

    /**
     * Assigning actions when a TextView is clicked within the fragment
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location:
                // launch the map fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MapFragment()).commit();
                break;
        }
    }
}
