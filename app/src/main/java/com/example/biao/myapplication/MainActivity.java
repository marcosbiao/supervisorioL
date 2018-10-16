package com.example.biao.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static List<objEsp> listaObj = new ArrayList<>();
    private final Handler mHandler01 = new Handler();
    private final Handler mHandler02 = new Handler();
    private final Handler mHandler03 = new Handler();
    Random numRandomico = new Random();
    DataPoint ponto;
    GraphView graph01, graph02, graph03, graphNovo;
    int x1 = 0, x2 = 0, x3 = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    double myDouble = -242528463.775282;
    final long timeReference = System.currentTimeMillis() + ((long) (myDouble * 1000));
    int a1 = 0;
    TextView tv1, tv2, tv3, ap1, ap2, ap3;
    LinearLayout layout1;
    LayoutParams params1 = new LinearLayout.LayoutParams(
            /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
            /*height*/ 100,
            /*weight*/ 1.0f
    );
    int toogle1 = 0, toogle2 = 0, toogle3 = 0;
    objEsp[] vetOb = new objEsp[2];
    objEsp ob1 = new objEsp();
    objEsp ob2 = new objEsp();
    String nomeData = " ";
    int i = 0, t = 0;
    private int tempoDeColeta = 2000;//tempo em milisegundos
    private Runnable mTimer1, mTimer2, mTimer3;
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        System.out.println(timeReference);

        //necessario para permissão à memoria interna
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //telaScan();

        //graph01 = findViewById(R.id.graph01);
        //graph02 = findViewById(R.id.graph02);
        //graph03 = findViewById(R.id.graph03);
        //graphNovo = findViewById(R.id.graph);
        tv1 = findViewById(R.id.textView1);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        ap1 = findViewById(R.id.ap1);
        ap2 = findViewById(R.id.ap2);
        ap3 = findViewById(R.id.ap3);
        layout1 = findViewById(R.id.layout1);
        mChart = findViewById(R.id.chart1);


        criarNovoGrafico();

/*
        ob1.setApelido("Nome teste");
        ob1.setG(graph01);
        ob1.setMac("182231");
        ob1.setIp("192.168.1.125");
        ob1.setAlerta(0);
        ob1.setSeries(series01);
        ap1.setText(ob1.getApelido());
        vetOb[0] = ob1;

        ob2.setApelido("Nome teste2");
        ob2.setG(graph02);
        ob2.setMac("999999");
        ob2.setIp("192.168.1.121");
        ob2.setAlerta(0);
        ob2.setSeries(series02);
        ap2.setText(ob2.getApelido());
        vetOb[1] = ob2;
*/
        ap3.setText("Nome teste 3");
        lerConf2("Esp1");
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "Permissão Concedida", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public void criarNovoGrafico() {
        // enable description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);
        mChart.setScaleYEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(false);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        // set an alternative background color
        mChart.setBackgroundColor(Color.LTGRAY);

        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        mChart.setScaleMinima(10f, 1f);
        mChart.centerViewToY(10f, YAxis.AxisDependency.LEFT);


        // add empty data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(Typeface.DEFAULT);
        l.setTextColor(Color.WHITE);


        //eixo X
        XAxis x1 = mChart.getXAxis();
        x1.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        //x1.setCenterAxisLabels(true);
        //x1.setTypeface(Typeface.DEFAULT);
        x1.setTextColor(Color.WHITE);
        x1.setDrawGridLines(false);
        x1.setAvoidFirstLastClipping(true);
        x1.setEnabled(true);
        x1.setGranularityEnabled(true);
        x1.setGranularity(1f);
        //x1.setValueFormatter(new DateAxisValueFormatter(null));

        x1.setValueFormatter(new IAxisValueFormatter() {
            private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //long timestamp = timeReference + (long) value;
                //long millis = TimeUnit.HOURS.toMillis(timestamp);
                //return mFormat.format(new Date(millis));
                Date s = new Date(new Float(value).longValue() * 1000L);
                return mFormat.format(s);
            }
        });


        mChart.setVisibleYRange(-50f, 50f, YAxis.AxisDependency.LEFT);
        mChart.setAutoScaleMinMaxEnabled(false);


        YAxis leftAxis = mChart.getAxisLeft();
        //leftAxis.setTypeface(Typeface.DEFAULT);
        leftAxis.setUseAutoScaleMaxRestriction(false);
        leftAxis.setUseAutoScaleMinRestriction(false);
        leftAxis.setStartAtZero(false);
        leftAxis.setSpaceTop(50);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setGranularity(10f);
        leftAxis.calculate(-40f, 40f);
        leftAxis.setSpaceBottom(50);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMinValue(-50f);
        leftAxis.setAxisMaxValue(50f);
        leftAxis.setAxisMaximum(50f);
        leftAxis.setAxisMinimum(-50f);
        leftAxis.setLabelCount(6);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawZeroLine(false);

        //linha limite
        LimitLine li = new LimitLine(20, "Alarme");
        leftAxis.addLimitLine(li);


        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void addEntry() {
        LineData data = mChart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

            double myDouble = -242528463.775282;
            final long now = System.currentTimeMillis() + ((long) (myDouble * 1000));
            System.out.println(now);

            Calendar calendar = Calendar.getInstance();
            //data.addXValue("");
            System.out.println("calendar: " + new Date().getTime());
            data.addEntry(new Entry((long) new Date().getTime(), 30.7f), 0);
            mChart.notifyDataSetChanged();
            mChart.invalidate();
            mChart.setVisibleXRange(5, 6);
            mChart.moveViewToX(data.getXMax());
        }
    }

    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setDrawCircles(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(ColorTemplate.getHoloBlue());
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setCircleRadius(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }


    private void salvarArquivo(double i, String nome) {
        //criar pasta
        File folder = new File(Environment.getExternalStorageDirectory() + "/Controle_esp");
        if (!folder.exists()) {
            folder.mkdir();
        }
        //salvar arquivo dentro da pasta
        String nomeArquivo = nome + ".txt";
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Controle_esp/" + nomeArquivo);
        //System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
        try {
            FileOutputStream salvar = new FileOutputStream(arquivo, true);
            String conteudo = i + " " + new Date() + "\n";
            salvar.write(conteudo.getBytes());
            salvar.close();
            //Toast.makeText(this, "",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            //Toast.makeText(this, "Arquivo não encontrado",Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            //Toast.makeText(this, "Erro",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, TelaConfiguracao.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                addEntry();

                mHandler01.postDelayed(this, tempoDeColeta);
            }
        };
        mHandler01.postDelayed(mTimer1, tempoDeColeta);

    }

    @Override
    public void onPause() {
        mHandler01.removeCallbacks(mTimer1);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

    }


    @SuppressLint("WrongConstant")
    public void chamaTela(View v) {
        if (t == 0) {
            vetOb[0].getG().setVisibility(0x00000004);
            t = 1;
        } else {
            vetOb[0].getG().setVisibility(0x00000000);
            t = 0;
        }
    }

    public void c1(View v) {
        t = 1;
        if (toogle1 == 0) {
            //graphNovo.setVisibility(View.VISIBLE);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(
                    /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                    /*height*/ 300
            ));
            toogle1 = 1;
        } else {
            //graphNovo.setVisibility(View.INVISIBLE);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(
                    /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                    /*height*/ 0
            ));
            toogle1 = 0;
            toogle2 = 0;
            toogle3 = 0;
        }
    }

    public void c2(View v) {
        t = 2;
        if (toogle2 == 0) {
            //graphNovo.setVisibility(View.VISIBLE);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(
                    /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                    /*height*/ 300
            ));
            toogle2 = 1;
        } else {
            //graphNovo.setVisibility(View.INVISIBLE);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(
                    /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                    /*height*/ 0
            ));
            toogle1 = 0;
            toogle2 = 0;
            toogle3 = 0;
        }
    }

    public void c3(View v) {
        t = 3;
        if (toogle3 == 0) {
            //graphNovo.setVisibility(View.VISIBLE);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(
                    /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                    /*height*/ 300
            ));
            toogle3 = 1;
        } else {
            //graphNovo.setVisibility(View.INVISIBLE);
            layout1.setLayoutParams(new LinearLayout.LayoutParams(
                    /*width*/ ViewGroup.LayoutParams.MATCH_PARENT,
                    /*height*/ 0
            ));
            zerarTudo();
        }
    }


    public void alerta(int apelido) {
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("Alerta");
        ad.setMessage("Frezee " + apelido + " está quente");
    }

    public void criarObjetos() {
        objEsp ob1 = new objEsp();
        File folder = new File(Environment.getExternalStorageDirectory() + "/Controle_esp/esps");
        if (!folder.exists()) {
            folder.mkdir();
        }
        //salvar arquivo dentro da pasta
        String nomeArquivo = "esp01.txt";
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Controle_esp/esps/" + nomeArquivo);
        try {
            FileOutputStream salvar = new FileOutputStream(arquivo, true);

            salvar.close();
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Arquivo não encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }

        vetOb[0] = ob1;


    }

    public void gravarObj(objEsp a) {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Controle_esp");
        if (!folder.exists()) {
            folder.mkdir();
        }
        String nomeArquivo = a.getMac() + ".txt";
        try {
            Log.i("NOME", nomeArquivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo));
            objectOutputStream.writeObject(a);
            objectOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void zerarTudo() {
        toogle1 = 0;
        toogle2 = 0;
        toogle3 = 0;
    }


    public void lerConf2(String nome) {
        try {
            String lstrlinha;
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + nome + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(arq));
            while ((lstrlinha = br.readLine()) != null) {
                System.out.println(lstrlinha);
                Log.i("teste: ", lstrlinha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void telaScan() {
        Intent intent = new Intent(this, telaOpcao.class);
        startActivity(intent);
    }

    public void telaTeste() {
        Intent intent = new Intent(this, telaPrincipal.class);
        startActivity(intent);
    }

    public void novatela() {
        Intent intent = new Intent(this, telaOpcao.class);
        startActivity(intent);
    }


    public class DateAxisValueFormatter implements IAxisValueFormatter {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        private String[] mValues;

        public DateAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        //@override
        public String getFormattedValue(float value, AxisBase axis) {
            return sdf.format(new Date((long) value));
        }
    }


    class HourAxisValueFormatter implements IAxisValueFormatter {

        private long referenceTimestamp; // minimum timestamp in your data set
        private DateFormat mDataFormat;
        private Date mDate;

        public HourAxisValueFormatter(long referenceTimestamp) {
            this.referenceTimestamp = referenceTimestamp;
            this.mDataFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
            this.mDate = new Date();
        }

        /**
         * Called when a value from an axis is to be formatted
         * before being drawn. For performance reasons, avoid excessive calculations
         * and memory allocations inside this method.
         */

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // convertedTimestamp = originalTimestamp - referenceTimestamp
            long convertedTimestamp = (long) value;

            // Retrieve original timestamp
            long originalTimestamp = referenceTimestamp + convertedTimestamp;

            // Convert timestamp to hour:minute
            return getHour(originalTimestamp);
        }

        private String getHour(long timestamp) {
            try {
                mDate.setTime(timestamp * 1000);
                return mDataFormat.format(mDate);
            } catch (Exception ex) {
                return "xx";
            }
        }
    }


}
