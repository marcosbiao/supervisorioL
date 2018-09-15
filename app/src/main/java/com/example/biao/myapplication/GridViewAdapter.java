package com.example.biao.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import java.util.List;

/**
 * Created by biao on 11/08/2018.
 */

public class GridViewAdapter extends ArrayAdapter<objEsp> {

    DecoView decoView;
    private int mBackIndex;
    private int mSeries1Index;
    private final float mSeriesMax = 50f;


    public GridViewAdapter(Context context, int resouce, List<objEsp> objects){
        super(context, resouce,objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
        }
        objEsp esp = getItem(position);
        //decoView = v.findViewById(R.id.dynamicArcView);
        ImageView img = (ImageView) v.findViewById(R.id.imageView2);
        TextView txtTitle = (TextView) v.findViewById(R.id.textViewTeste);
        TextView txtDescription = (TextView) v.findViewById(R.id.textDescription);

        //img.setImageResource(esp.getImageId());
        txtTitle.setText(esp.getApelido());
        txtDescription.setText(esp.getTemperatura());




        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#51c7dd"))
                .setRange(0, 10, 5)
                .build();

        decoView.addSeries(seriesItem);

        decoView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .setLineWidth(32f)
                .build());


        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0, mSeriesMax, 0)
                .setInitialVisibility(true)
                .build();

        decoView.addSeries(seriesItem2);


        return v;
    }






}
