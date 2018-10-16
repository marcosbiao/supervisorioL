package com.example.biao.myapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by biao on 16/08/2018.
 */

public class GridViewAdapter2 extends ArrayAdapter<objEsp> {


    public GridViewAdapter2(Context context, int resouce, List<objEsp> objects) {
        super(context, resouce, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item2, null);
        }
        objEsp esp = getItem(position);
        ImageView circulo = (ImageView) v.findViewById(R.id.imageViewCirculo);

        TextView txtTemperatura = (TextView) v.findViewById(R.id.textViewTemperatura);
        txtTemperatura.setTypeface(null, Typeface.BOLD);

        TextView txtApelido = (TextView) v.findViewById(R.id.textViewApelido);
        txtApelido.setTypeface(null, Typeface.BOLD);
        txtApelido.setTextSize(16);

        TextView txtSP = v.findViewById(R.id.textViewSP);
        TextView txtAL = v.findViewById(R.id.textViewAlarme);

        txtSP.setText("SP: " + String.valueOf(esp.getSp()) + "ºC");
        txtAL.setText("AL: " + String.valueOf(esp.getAlerta()) + "ºC");

        if (esp.getIndicador() == 0) {
            //lamp.setImageResource(R.mipmap.ic_lamp_off);
        } else if (esp.getIndicador() == 1) {
            //lamp.setImageResource(R.mipmap.ic_lamp_on);
        }

        if (esp.getStatus() == 0) {
            circulo.setImageResource(R.mipmap.ic_circule_green);
        } else if (esp.getStatus() == 1) {
            circulo.setImageResource(R.mipmap.ic_circule_yellow);
        } else {
            circulo.setImageResource(R.mipmap.ic_circule_red);
        }

        //img.setImageResource(esp.getImageId());
        txtApelido.setText(esp.getApelido());
        txtTemperatura.setText(esp.getTemperatura() + "ºC");

        return v;
    }

}
