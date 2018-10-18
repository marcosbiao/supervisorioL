package com.example.biao.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TelaHistorico extends AppCompatActivity {
    List<String> nomes = new ArrayList<>();
    List<String> listMes = new ArrayList<>();
    List<String> listAno = new ArrayList<>();
    List<String> macs = new ArrayList<>();
    String[] arraySpinerDia = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"
            , "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    Spinner spNome, spMes, spAno, spDia;
    Button btAbrir;
    String selectNome, selectMes, selectAno, selectDia;
    GraphView gv;
    int posicao;

    //    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());
    private LineChart mChart;
    private LineDataSet dataset;
    private double startingSample = 0;
    private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss", new Locale("pt_BR", "Brazil"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_historico);

        spNome = findViewById(R.id.spinnerNomes);
        spMes = findViewById(R.id.spinnerMes);
        spAno = findViewById(R.id.spinnerAno);
        spDia = findViewById(R.id.spinnerDia);
        btAbrir = findViewById(R.id.buttonAbrir);
        //gv = findViewById(R.id.graphHistorico);
        //gv.setVisibility(View.INVISIBLE);
        mChart = findViewById(R.id.chartHistorico);

        Calendar calendario1 = Calendar.getInstance();
        calendario1.set(2017, 10, 3, 0, 0, 0);
        startingSample = calendario1.getTimeInMillis();

        carregarNomes();
        mostradorDeNomes();
        carregarSpinners();
        criarGrafico();

        btAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNome = spNome.getSelectedItem().toString();
                posicao = spNome.getSelectedItemPosition();
                selectMes = String.valueOf(spMes.getSelectedItem());
                selectAno = String.valueOf(spAno.getSelectedItem());
                selectDia = spDia.getSelectedItem().toString();
                System.out.println("Nome: " + selectNome + " Mes: " + selectMes + " Ano: " + selectAno);

                //gv.removeAllSeries();
                //gv.refreshDrawableState();
                buscador(selectNome, selectMes, selectAno, selectDia, posicao);
//                criarGrafico();
            }
        });
    }

    public void carregarNomes() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Controle_esp/Dados");
        if (!folder.exists()) {
            folder.mkdir();
        }
        String[] aux = folder.list();
        if (aux.length == 0) {
            Toast.makeText(this, "Sem dados para resgatar", Toast.LENGTH_SHORT).show();
            finish();
        }
        for (String anAux : aux) {
            String[] temp;
            temp = anAux.split("_", 4);
            System.out.println(temp[0]);
            if (verificadorNomes(temp[0], macs)) {
                macs.add(temp[0]);
                objEsp novo = buscadorApelido(temp[0]);
                nomes.add(novo.getApelido());
            }
            if (verificadorNomes(temp[1], listAno)) {
                listAno.add(temp[1]);
            }
            if (verificadorNomes(temp[2], listMes)) {
                listMes.add(temp[2]);
            }
        }
        organizadorMes(listMes);
    }

    public boolean verificadorNomes(String nome, List<String> lista) {
        boolean b = true;
        if (lista.size() > 0) {
            for (int i = 0; i < lista.size(); i++) {
                if (nome.equals(lista.get(i))) {
                    System.out.println(nome + " - " + lista.get(i) + " -> ");
                    b = false;
                    break;
                } else {
                    b = true;
                }
            }
        }
        return b;
    }

    public void mostradorDeNomes() {
        for (int i = 0; i < nomes.size(); i++) {
            System.out.println("Nome: " + nomes.get(i));
        }
        for (int i = 0; i < listMes.size(); i++) {
            System.out.println("Mes: " + listMes.get(i));
        }
        for (int i = 0; i < listAno.size(); i++) {
            System.out.println("Ano: " + listAno.get(i));
        }
    }

    public void organizadorMes(List<String> lista) {
        for (int i = 0; i < lista.size(); i++) {
            switch (lista.get(i)) {
                case "1":
                    lista.set(i, "Janeiro");
                    break;
                case "2":
                    lista.set(i, "Fevereiro");
                    break;
                case "3":
                    lista.set(i, "Março");
                    break;
                case "4":
                    lista.set(i, "Abril");
                    break;
                case "5":
                    lista.set(i, "Maio");
                    break;
                case "6":
                    lista.set(i, "Junho");
                    break;
                case "7":
                    lista.set(i, "Julho");
                    break;
                case "8":
                    lista.set(i, "Agosto");
                    break;
                case "9":
                    lista.set(i, "Setembro");
                    break;
                case "10":
                    lista.set(i, "Outubro");
                    break;
                case "11":
                    lista.set(i, "Novembro");
                    break;
                case "12":
                    lista.set(i, "Dezembro");
                    break;
                default:
                    System.out.println("Default");
            }
        }
        Collections.sort(listMes);
    }

    public void carregarSpinners() {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomes);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spNome.setAdapter(dataAdapter);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterMes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listMes);
        // Drop down layout style - list view with radio button
        dataAdapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spMes.setAdapter(dataAdapterMes);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterAno = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listAno);
        // Drop down layout style - list view with radio button
        dataAdapterAno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spAno.setAdapter(dataAdapterAno);

        ArrayAdapter<String> dataAdapterDia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinerDia);
        dataAdapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDia.setAdapter(dataAdapterDia);
    }

    public void criarGrafico() {
        XAxis xAxis = mChart.getXAxis();
        YAxis yAxis = mChart.getAxisLeft();

        mChart.getDescription().setEnabled(true);
        // enable touch gestures
        mChart.setTouchEnabled(true);
        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setAutoScaleMinMaxEnabled(true);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        dataset = new LineDataSet(new ArrayList<Entry>(), "Temperatura");
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.setColor(ColorTemplate.getHoloBlue());
        dataset.setValueTextColor(ColorTemplate.getHoloBlue());
        dataset.setLineWidth(1.5f);
        dataset.setDrawCircles(true);
        dataset.setDrawValues(false);
        dataset.setMode(LineDataSet.Mode.LINEAR);
        dataset.setFillAlpha(65);
        dataset.setFillColor(ColorTemplate.getHoloBlue());
        dataset.setHighLightColor(Color.rgb(244, 117, 117));
        dataset.setDrawCircleHole(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setTextSize(14f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(true);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
        xAxis.setCenterAxisLabels(true);
        // xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                System.out.println("Valor formatado: " + value);
                String format = mFormat.format(new Date((long) (value + startingSample)));
                return format;
            }
        });

        yAxis.setTypeface(Typeface.DEFAULT);
        yAxis.setTextSize(14f);
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxis.setTextColor(ColorTemplate.getHoloBlue());
        yAxis.setDrawGridLines(true);
        yAxis.setGranularityEnabled(true);
        yAxis.setGranularity(1f);
        yAxis.setLabelCount(6);
        yAxis.setUseAutoScaleMaxRestriction(true);
        yAxis.setTextColor(Color.rgb(255, 192, 56));
        yAxis.setCenterAxisLabels(true);

        //atualizando os dados
        mChart.invalidate();
    }

    public long tempo() {
        long time = System.currentTimeMillis();
        // long timeSecs = (long)(time / 1000) * 1000;
        System.out.println("Tempo (ms): " + time);
        // System.out.println("Tempo (s): " + timeSecs);
        return time;
    }

    public objEsp buscador(String nome, String mes, String ano, String dia, int pos) {
        objEsp ativo = new objEsp();
        String lstrlinha;
        try {
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + macs.get(pos) + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(arq));
            lstrlinha = br.readLine();
            //System.out.println(lstrlinha);
            lstrlinha = br.readLine();
            //System.out.println(lstrlinha);
            lstrlinha = br.readLine();
            //System.out.println(lstrlinha);
            ativo.setApelido(lstrlinha);
            lstrlinha = br.readLine();
            ativo.setAlerta(Float.parseFloat(lstrlinha));
            ativo.setMac(nome);

            mes = voltadorDeMes(mes);

            String nomeArquivo = macs.get(pos) + "_" + ano + "_" + mes + "_" + dia;
            File arq2 = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/Dados/" + nomeArquivo + ".txt");
            BufferedReader br2 = new BufferedReader(new FileReader(arq2));
            if (!arq2.exists()) {
                System.out.println("Não existe dados para " + nome + " nesse dia");
                Toast.makeText(TelaHistorico.this, "Não existe dados para " + nome + " nesse dia", Toast.LENGTH_SHORT).show();
                gv.setVisibility(View.INVISIBLE);
            }
            System.out.println("ANTES DO WHILE");
            //series.resetData(new DataPoint[]{});
            dataset.clear();
            String[] temp;
            float temperatura;
            float tempo;
            while ((lstrlinha = br2.readLine()) != null) {
                temp = lstrlinha.split(";", 2);
                temperatura = Float.parseFloat(temp[0]);
                tempo = Float.parseFloat(temp[1]);
                Entry entry = new Entry(tempo, temperatura);
                dataset.addEntry(entry);
            }
            System.out.println("FIM DO WHILE");

            mChart.setData(new LineData(dataset));
            mChart.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(TelaHistorico.this, "Não existe dados para " + nome + " nesse dia", Toast.LENGTH_SHORT).show();
            //gv.setVisibility(View.INVISIBLE);
        }
        return ativo;
    }

    public DataPoint[] getDataPoint() {
        return new DataPoint[]{
                new DataPoint(0, 0.0)
        };
    }

    public String voltadorDeMes(String mes) {
        switch (mes) {
            case "Janeiro":
                mes = "1";
                break;
            case "Fevereiro":
                mes = "2";
                break;
            case "Março":
                mes = "3";
                break;
            case "Abril":
                mes = "4";
                break;
            case "Maio":
                mes = "5";
                break;
            case "Junho":
                mes = "6";
                break;
            case "Julho":
                mes = "7";
                break;
            case "Agosto":
                mes = "8";
                break;
            case "Setembro":
                mes = "9";
                break;
            case "Outubro":
                mes = "10";
                break;
            case "Novembro":
                mes = "11";
                break;
            case "Dezembro":
                mes = "12";
                break;
            default:
                break;
        }
        return mes;
    }

    public objEsp buscadorApelido(String mac) {
        objEsp ativo = new objEsp();
        String lstrlinha;
        try {
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + mac + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(arq));
            lstrlinha = br.readLine(); //MAC
            lstrlinha = br.readLine();  //IP
            lstrlinha = br.readLine();   //Apelido
            ativo.setApelido(lstrlinha);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ativo;
    }
}
