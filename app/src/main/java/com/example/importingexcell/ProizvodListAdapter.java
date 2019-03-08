package com.example.importingexcell;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProizvodListAdapter extends ArrayAdapter<Proizvod> {
    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView id;
        TextView ime;
        TextView kolicina;
    }

    /**
     * Default constructor for the PersonListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ProizvodListAdapter(Context context, int resource, ArrayList<Proizvod> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String id = getItem(position).getId();
        String ime = getItem(position).getIme();
        String kolicina = getItem(position).getKolicina();

        //Create the person object with the information
        Proizvod proizvod = new Proizvod(id,ime,kolicina);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.tvId);
            holder.ime = (TextView) convertView.findViewById(R.id.tvIme);
            holder.kolicina = (TextView) convertView.findViewById(R.id.tvKolicina);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        /*Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);*/
        lastPosition = position;

        holder.id.setText(proizvod.getId());
        holder.ime.setText(proizvod.getIme());
        holder.kolicina.setText(proizvod.getKolicina());


        return convertView;
    }
}
