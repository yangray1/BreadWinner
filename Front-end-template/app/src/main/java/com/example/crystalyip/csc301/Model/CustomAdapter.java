package com.example.crystalyip.csc301.Model;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crystalyip.csc301.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<Listing> listings;

    public CustomAdapter(Context c, ArrayList<Listing> listings) {
        this.c = c;
        this.listings = listings;
    }

    @Override
    public int getCount() {
        return listings.size();
    }

    @Override
    public Object getItem(int i) {
        return listings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.menu_item,viewGroup,false);
        }
        final Listing s= (Listing) this.getItem(i);
        ImageView img= (ImageView) view.findViewById(R.id.food_image_display);
        TextView foodDescription= (TextView) view.findViewById(R.id.food_description);
        String descriptionString=" $"+s.getPrice()+" "+s.getFoodName()+"\n "+s.getLocation();
        SpannableString description = new SpannableString(descriptionString);
        description.setSpan(new RelativeSizeSpan(1.2f), 0, descriptionString.indexOf("\n"), 0);
        foodDescription.setText(description);
        img.setImageBitmap(s.getImageBytes());

        return view;
    }
}
