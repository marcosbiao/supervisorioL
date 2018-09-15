package com.example.biao.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.biao.myapplication.telaPrincipal.espList;


public class TelaConfiguracao extends AppCompatActivity {

    Button bt;
    EditText etApelido, etAlerta, etSP;
    TextView tvNome, tvLimite;
    String mac,ip,apelido;
    float alerta;
    int position;
    CheckBox cbMax,cbMin;
    objEsp ob = new objEsp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_configuracao);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        etApelido = findViewById(R.id.editTextApelido);
        tvNome = findViewById(R.id.textViewNome);
        etAlerta = findViewById(R.id.editTextAlerta);
        bt = findViewById(R.id.buttonSalvar);
        etSP = findViewById(R.id.editTextSP);
        tvLimite = findViewById(R.id.textViewValorLimite);
        cbMax = findViewById(R.id.checkBoxMax);
        cbMin = findViewById(R.id.checkBoxMin);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mac = bundle.getString("mac");
        ip = bundle.getString("ip");
        etApelido.setText(bundle.getString("apelido"));
        System.out.println((int) bundle.getFloat("alerta"));
        etAlerta.setText(String.valueOf(bundle.getFloat("alerta")));
        position = bundle.getInt("position");
        etSP.setText(String.valueOf(bundle.getFloat("sp")));

        ajustarLimites();
        mostrarLImites();
        if(bundle.getBoolean("max")){
            cbMax.setChecked(true);
        }else{
            cbMax.setChecked(false);
        }
        if(bundle.getBoolean("min")){
            cbMin.setChecked(true);
        }else{
            cbMin.setChecked(false);
        }


        etAlerta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String auxMax, auxMin;
                auxMax =String.valueOf(  Float.parseFloat(etSP.getText().toString()) + Float.parseFloat(etAlerta.getText().toString())         );
                auxMin = String.valueOf(  Float.parseFloat(etSP.getText().toString()) - Float.parseFloat(etAlerta.getText().toString())         );
                tvLimite.setText(auxMin + " ou " + auxMax);
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustarLimites();
                System.out.println("Limite max: "+ ob.isLiMax()+"  :"+ ob.getLimiteMax());
                ob.setMac(mac);
                ob.setIp(ip);
                ob.setApelido(etApelido.getText().toString());
                ob.setAlerta(Float.parseFloat(etAlerta.getText().toString()));
                ob.setSp(Float.parseFloat(etSP.getText().toString()));
                salvarDadosEsp(ob);
                espList.remove(position);
                espList.add(ob);
                telaPrincipal.flag = 1;
                finish();
            }
        });







        //necessario para permissão à memoria interna
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch(requestCode){
            case 1000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permissão Concedida", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public void salvarDadosEsp(objEsp oe){
        try {
            byte[] dados;
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/"+oe.getMac() +".txt");
            FileOutputStream fos;
            limparArquivo(oe.getMac());
            dados = (oe.getMac() +"\n" + oe.getIp()+"\n" + oe.getApelido() +"\n" + oe.getAlerta() +"\n"+oe.getSp()+"\n"+oe.isLiMax()+"\n"+oe.isLiMin()+"\n").getBytes();
            fos = new FileOutputStream(arq,true);
            fos.write(dados);
            fos.flush();
            fos.close();
            //System.out.println("GRAVEI!!!!!");
            //Log.i("teste: ", "GRAVEI!!!!!");
        }catch (Exception e){
            Toast.makeText(this, "Erro",Toast.LENGTH_SHORT).show();
        }
    }

    private void limparArquivo(String t){
        //criar pasta
        File folder = new File(Environment.getExternalStorageDirectory() + "/Controle_esp");
        if(!folder.exists()){
            folder.mkdir();
        }
        //salvar arquivo dentro da pasta
        String nomeArquivo = t +".txt";
        File arquivo = new File(Environment.getExternalStorageDirectory(),"/Controle_esp/" + nomeArquivo);
        System.out.println("/Controle_esp/" + nomeArquivo);
        try{
            FileOutputStream salvar = new FileOutputStream(arquivo);
            String conteudo = "";
            salvar.write(conteudo.getBytes());
            salvar.close();
            //Toast.makeText(this, "",Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException e){
            Toast.makeText(this, "Arquivo não encontrado",Toast.LENGTH_SHORT).show();
        }catch (IOException ioe){
            Toast.makeText(this, "Erro",Toast.LENGTH_SHORT).show();
        }

    }

    public void ajustarLimites(){
        if(cbMax.isChecked()){
            ob.setLiMax(true);
            ob.setLimiteMax(Float.parseFloat(etSP.getText().toString()) + Float.parseFloat(etAlerta.getText().toString()));
        }else{
            ob.setLiMax(false);
        }

        if(cbMin.isChecked()){
            ob.setLiMin(true);
            ob.setLimiteMax(Float.parseFloat(etSP.getText().toString()) - Float.parseFloat(etAlerta.getText().toString()));
        }else{
            ob.setLiMin(false);
        }
    }

    public void mostrarLImites(){
        String auxMax, auxMin;
        auxMax =String.valueOf(  Float.parseFloat(etSP.getText().toString()) + Float.parseFloat(etAlerta.getText().toString())         );
        auxMin = String.valueOf(  Float.parseFloat(etSP.getText().toString()) - Float.parseFloat(etAlerta.getText().toString())         );
        tvLimite.setText(auxMin + " ou " + auxMax);
    }




}
