package com.example.biao.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class telaOpcao extends AppCompatActivity {

    GraphView g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_opcao);


        g = findViewById(R.id.grafico);


        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(d1, 1),
                new DataPoint(d2, 5),
                new DataPoint(d3, 3)
        });


        //g.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(telaOpcao.this));
        final SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
        g.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {

                    return formato.format(value); // padrao
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        g.getGridLabelRenderer().setNumHorizontalLabels(3);
        g.addSeries(series);
        g.setTitle("lalala");
        g.getViewport().setMinX(d1.getTime());
        g.getViewport().setMaxX(d3.getTime());
        g.getViewport().setXAxisBoundsManual(true);

        g.getGridLabelRenderer().setHumanRounding(false);


    }

}
