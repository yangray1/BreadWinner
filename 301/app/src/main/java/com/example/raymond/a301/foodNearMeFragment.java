package com.example.raymond.a301;


import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class foodNearMeFragment extends Fragment {

    int[] IMAGES = {R.drawable.rice};
    String[] names = {"Rice"};
    String[] location = {"999 Pape street"};
    String[] date = {"10/10/18"};

    public foodNearMeFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_near_me, container, false);

//        ListView listView = (ListView) view.findViewById(R.id.lstFoodList);
//
//        ArrayAdapter<String> listViewadapter = new ArrayAdapter<String>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                names);
//        listView.setAdapter(listViewadapter);

        return view;
    }

}
