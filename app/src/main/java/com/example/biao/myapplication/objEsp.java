package com.example.biao.myapplication;

import android.app.AlertDialog;
import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import static android.app.PendingIntent.getActivity;


/**
 * Created by biao on 13/06/2018.
 */

public class objEsp {

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


    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());
    private LineGraphSeries<DataPoint> seriesAlarme = new LineGraphSeries<>(getDataPoint());

    //Construtores
    public objEsp(){
        adicionar();
    }

    public objEsp(String mac){
        this.mac = mac;
        adicionar();
    }

    public objEsp(String apelido, GraphView g ){
        this.apelido=apelido;
        this.g=g;
        adicionar();
    }

    public objEsp(String apelido, String temperatura){
        this.apelido=apelido;
        this.temperatura=temperatura;
        adicionar();
    }

    //Metodos

    public DataPoint[] getDataPoint(){
        DataPoint[] dp = new DataPoint[]{
                new DataPoint(0,0 )
        };
        return dp;
    }

    public void adicionar(){
        series.appendData(new DataPoint(0,0),true,20);
        Status=1;
        indicador=0;
        temperatura = "0";
        sp = -70;
        series.setDrawBackground(true);
        series.setThickness(3);
        seriesAlarme.setColor(Color.RED);
        seriesAlarme.setThickness(3);

    }

    public static final Comparator<objEsp> POR_STATUS = new Comparator<objEsp>(){
        public int compare(objEsp a1, objEsp a2) {
            return compare(a1,a2);
        }
    };


    @Override
    public String toString(){
        series.toString();
        return "\n -> [AP: " + apelido+" | temp:" + temperatura + " | "+ series + "]";
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





}
