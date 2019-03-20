package com.example.importingexcell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PrethodneIzmene_ListAdapter_v2 extends ArrayAdapter<String> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView logString;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public PrethodneIzmene_ListAdapter_v2(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String stringZaIspis = getItem(position).toString();
/*
        //Create the person object with the information
        Proizvod proizvod = new Proizvod(id, ime, kolicina, sabiranje, "");*/

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.logString = (TextView) convertView.findViewById(R.id.textViewLogString);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;

        holder.logString.setText(stringZaIspis);

        return convertView;
    }
}
