package com.example.crystalyip.csc301;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        Button searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String searchQuery = getSearchQuery(view); // find what was typed in the edittext field
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FoodListingNearMe(searchQuery)).commit(); // fragment switch
            }
        });

        return view;
    }

    /**
     * Return the search query found in the search bar in view.
     * @param view view that contains the search bar to get the query from
     * @return query found in search bar
     * */
    private String getSearchQuery(View view) {
        EditText searchBar = view.findViewById(R.id.search_field2);
        return searchBar.getText().toString();
    }

}
