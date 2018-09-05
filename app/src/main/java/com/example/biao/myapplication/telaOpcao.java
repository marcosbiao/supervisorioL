package com.example.biao.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

public class telaOpcao extends AppCompatActivity {

    Button btManter, btReconfigurar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_opcao);


        DecoView decoView = (DecoView) findViewById(R.id.dynamicArcView);

        final SeriesItem seriesItem = new SeriesItem.Builder(Color.parseColor("#51c7dd"))
                .setRange(0, 10, 5)
                .build();

        decoView.addSeries(seriesItem);

        decoView.addSeries(seriesItem);



        decoView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 100)
                .setInitialVisibility(true)
                .setLineWidth(32f)
                .build());

//Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                .setRange(0, 100, 0)
                .setInitialVisibility(true)
                .setLineWidth(32f)
                .build();
        decoView.addSeries(seriesItem1);




        final TextView textPercentage = (TextView) findViewById(R.id.textView);
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                float percentFilled = ((currentPosition - seriesItem.getMinValue()) / (seriesItem.getMaxValue() - seriesItem.getMinValue()));
                textPercentage.setText(String.format("%.0f%%", percentFilled * 100f));
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });



    }

}
