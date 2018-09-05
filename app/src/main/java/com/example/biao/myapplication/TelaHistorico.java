package com.example.biao.myapplication;

import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TelaHistorico extends AppCompatActivity {

    List<String> nomes = new ArrayList<>();
    List<String> listMes = new ArrayList<>();
    List<String> listAno = new ArrayList<>();
    List<String> macs = new ArrayList<>();
    String[] arraySpinerDia = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19"
            ,"20","21","22","23","24","25","26","27","28","29","30","31"};
    Spinner spNome, spMes, spAno,spDia;
    Button btAbrir;
    String selectNome, selectMes, selectAno,selectDia;
    GraphView gv;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());
    int posicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_historico);

        spNome = findViewById(R.id.spinnerNomes);
        spMes = findViewById(R.id.spinnerMes);
        spAno = findViewById(R.id.spinnerAno);
        spDia = findViewById(R.id.spinnerDia);
        btAbrir = findViewById(R.id.buttonAbrir);
        gv = findViewById(R.id.graphHistorico);
        gv.setVisibility(View.INVISIBLE);

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

                gv.removeAllSeries();
                gv.refreshDrawableState();
                buscador(selectNome, selectMes, selectAno,selectDia,posicao);


                criarGrafico();

            }
        });


    }


    public void carregarNomes() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/Controle_esp/Dados");
        if(!folder.exists()){
            folder.mkdir();
        }
        String[] aux = folder.list();
        if(aux.length ==0){
            Toast.makeText(this, "Sem dados para resgatar",Toast.LENGTH_SHORT).show();
            finish();
        }
        for (int i = 0; i < aux.length; i++) {
            String[] temp = new String[4];
            temp = aux[i].split("_", 4);
            if (verificadorNomes(temp[0], nomes) == true) {
                macs.add(temp[0]);
                objEsp novo = buscadorApelido(temp[0]);
                nomes.add(novo.getApelido());
            }
            if (verificadorNomes(temp[1], listAno) == true) {
                listAno.add(temp[1]);
            }
            if (verificadorNomes(temp[2], listMes) == true) {
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomes);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spNome.setAdapter(dataAdapter);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterMes = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listMes);
        // Drop down layout style - list view with radio button
        dataAdapterMes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spMes.setAdapter(dataAdapterMes);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterAno = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listAno);
        // Drop down layout style - list view with radio button
        dataAdapterAno.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spAno.setAdapter(dataAdapterAno);

        ArrayAdapter<String> dataAdapterDia = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinerDia);
        dataAdapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDia.setAdapter(dataAdapterDia);

    }

    public void criarGrafico() {
        gv.getViewport().setXAxisBoundsManual(true);
        gv.getViewport().setMinX(0);
        gv.getViewport().setMaxX(10);

        // permite setar os limites do gráfico
        gv.getViewport().setYAxisBoundsManual(true);
        gv.getViewport().setMinY(15);
        gv.getViewport().setMaxY(-15);
        gv.setTitleColor(Color.GRAY);
        gv.setTitleTextSize(30);
        gv.getGridLabelRenderer().setGridColor(Color.GRAY);


        gv.setBackgroundColor(Color.argb(50,255,255,255));
        gv.getViewport().setScrollable(true);// pode ir e voltar no grafico

    }

    public objEsp buscador(String nome, String mes, String ano,String dia,int pos) {
        objEsp ativo = new objEsp();
        String lstrlinha;
        try {
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + macs.get(pos).toString() + ".txt");
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

            String nomeArquivo = macs.get(pos).toString() + "_" + ano + "_" + mes+"_"+ dia;
            File arq2 = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/Dados/" + nomeArquivo + ".txt");
            BufferedReader br2 = new BufferedReader(new FileReader(arq2));
            if(!arq2.exists()){
                System.out.println("Não existe dados para "+ nome + " nesse dia");
                Toast.makeText(TelaHistorico.this, "Não existe dados para "+ nome + " nesse dia", Toast.LENGTH_SHORT).show();
                gv.setVisibility(View.INVISIBLE);
            }
            String[] temp = new String[4];
            String hora, minuto;
            String numb;
            DataPoint ponto;
            int x1 = 0;
            System.out.println("ANTES DO WHILE");
            //series.resetData(new DataPoint[]{});


            int cont=0;
            while ((lstrlinha = br2.readLine()) != null) {
                System.out.println("DENTRO DO WHILE");
                temp = lstrlinha.split(";", 4);
                numb = temp[0];
                hora = temp[1];
                minuto = temp[2];
                x1++;
                System.out.println(numb + "  " + dia + "  " + "   " + hora + "  " + minuto);
                System.out.println("Depois "+x1+"+: ");
                cont++;
                //int auxInt = Integer.parseInt(numb.toString());
                ponto = new DataPoint(x1, 5);
                series.appendData(ponto, false, 500);
            }
            series.setDrawBackground(true);
            gv.setVisibility(View.VISIBLE);
            gv.removeAllSeries();
            gv.addSeries(series);
            System.out.println(arq2.getName());



        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(TelaHistorico.this, "Não existe dados para "+ nome + " nesse dia", Toast.LENGTH_SHORT).show();
            gv.setVisibility(View.INVISIBLE);
        }
        return ativo;
    }


    public DataPoint[] getDataPoint() {
        DataPoint[] dp = new DataPoint[]{
                new DataPoint(0, 0.0)
        };
        return dp;
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


    public objEsp buscadorApelido(String mac){
        objEsp ativo = new objEsp();
        String lstrlinha;
        try {
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + mac + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(arq));
            lstrlinha = br.readLine(); //MAC
            lstrlinha = br.readLine();  //IP
            lstrlinha = br.readLine();   //Apelido
            ativo.setApelido(lstrlinha);

        }catch (IOException e){
            e.printStackTrace();
        }
        return ativo;
    }



}
