package com.example.biao.myapplication;

import android.graphics.Color;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;


/**
 * Created by biao on 13/06/2018.
 */

public class ObjEsp {
    public static final Comparator<ObjEsp> POR_STATUS = new Comparator<ObjEsp>() {
        public int compare(ObjEsp a1, ObjEsp a2) {
            return compare(a1, a2);
        }
    };

    private String mac;
    private String ip;
    private String apelido;
    private String temperatura;
    private int tensao;
    private String voltagem;
    private String atividade;
    private GraphView g;
    private int Status; //0 = verde, 1=amarelo, 2=temperatura 3=conexao 4=tensao
    private int indicador; //0=não conectado, 1=conectado
    private float alerta;//valor limite para gerar alerta
    private float sp;
    private float limiteMax, limiteMin;
    private boolean liMax, liMin;
    private LineDataSet dataset;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());
    private LineGraphSeries<DataPoint> seriesAlarme = new LineGraphSeries<>(getDataPoint());

    //Construtores
    public ObjEsp() {
        adicionar();
    }

    public ObjEsp(String mac) {
        this.mac = mac;
        adicionar();
    }

    public ObjEsp(String apelido, GraphView g) {
        this.apelido = apelido;
        this.g = g;
        adicionar();
    }

    //Metodos

    public ObjEsp(String apelido, String temperatura) {
        this.apelido = apelido;
        this.temperatura = temperatura;
        adicionar();
    }

    public DataPoint[] getDataPoint() {
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        DataPoint[] dp = new DataPoint[]{
                //new DataPoint(d1, 1),
                //new DataPoint(d2, 0),
                //new DataPoint(d3, 1)
        };
        return dp;
    }

    public void adicionar() {
        series.appendData(new DataPoint(0, 0), true, 20);
        Status = 1;
        indicador = 0;
        temperatura = "0";
        sp = -70;
        series.setDrawBackground(true);
        series.setThickness(3);
        seriesAlarme.setColor(Color.RED);
        seriesAlarme.setThickness(3);
        setLiMax(false);
        setLiMin(false);

        dataset = new LineDataSet(new ArrayList<Entry>(), "Temperatura");
        dataset.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.setColor(ColorTemplate.getHoloBlue());
        dataset.setValueTextColor(ColorTemplate.getHoloBlue());
        dataset.setLineWidth(1.5f);
        dataset.setDrawCircles(true);
        dataset.setDrawValues(false);
        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataset.setFillAlpha(65);
        dataset.setFillColor(ColorTemplate.getHoloBlue());
        dataset.setHighLightColor(Color.rgb(244, 117, 117));
        dataset.setDrawCircleHole(false);

    }

    @Override
    public String toString() {
        series.toString();
        return "\n -> [AP: " + apelido + " | temp:" + temperatura + " | " + series + "]";
    }


    //Set e Get

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public GraphView getG() {
        return g;
    }

    public void setG(GraphView g) {
        this.g = g;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public float getAlerta() {
        return alerta;
    }

    public void setAlerta(float alerta) {
        this.alerta = alerta;
    }

    public LineGraphSeries<DataPoint> getSeries() {
        return series;
    }

    public void setSeries(LineGraphSeries<DataPoint> series) {
        this.series = series;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(String voltagem) {
        this.voltagem = voltagem;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public int getIndicador() {
        return indicador;
    }

    public void setIndicador(int indicador) {
        this.indicador = indicador;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getTensao() {
        return tensao;
    }

    public void setTensao(int tensao) {
        this.tensao = tensao;
    }

    public float getSp() {
        return sp;
    }

    public void setSp(float sp) {
        this.sp = sp;
    }

    public LineGraphSeries<DataPoint> getSeriesAlarme() {
        return seriesAlarme;
    }

    public void setSeriesAlarme(LineGraphSeries<DataPoint> seriesAlarme) {
        this.seriesAlarme = seriesAlarme;
    }

    public float getLimiteMax() {
        return limiteMax;
    }

    public void setLimiteMax(float limiteMax) {
        this.limiteMax = limiteMax;
    }

    public float getLimiteMin() {
        return limiteMin;
    }

    public void setLimiteMin(float limiteMin) {
        this.limiteMin = limiteMin;
    }

    public boolean isLiMax() {
        return liMax;
    }

    public void setLiMax(boolean liMax) {
        this.liMax = liMax;
    }

    public boolean isLiMin() {
        return liMin;
    }

    public void setLiMin(boolean liMin) {
        this.liMin = liMin;
    }

    public LineDataSet getDataset() {
        return dataset;
    }

    public void setDataset(LineDataSet dataset) {
        this.dataset = dataset;
    }
}
