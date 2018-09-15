package com.example.biao.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Telephony;
import android.provider.Telephony.Sms;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.SEND_SMS;

public class TelaMensagem extends AppCompatActivity {

    static String mensagem = "Carai", numeroFone="075991267012";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0 ;
    static int cont=0;
    //static Switch tb;
    static boolean estadoSwitch = false;
    //static ToggleButton tb ;
    Button btSalvar, btList;
    EditText numeroMensagem,nomeTelefone;
    //static List<String> listaTelefone = new ArrayList<>();
    ListView listViewTelefone;
    ViewStub stubList;
    static List<objTelefone> listaObjTelefone = new ArrayList<>();
    ListViewAdapter listAdapter;
    CheckBox cbConect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_mensagem);

        //tb = findViewById(R.id.toggleButtonMensagem);
        //tb = findViewById(R.id.switchMensagem);
        btSalvar = findViewById(R.id.buttonSalvarNumero);
        numeroMensagem = findViewById(R.id.editTextNumeroMensagem);
        nomeTelefone = findViewById((R.id.editTextNomeTelefone));
        btList = findViewById(R.id.buttonSalvarList);


        //carregando ListView
        lerNumero();
        criarList();








        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarNumero(numeroMensagem.getText().toString(),nomeTelefone.getText().toString());
                listAdapter = new ListViewAdapter(TelaMensagem.this,R.layout.list_item,listaObjTelefone);
                listAdapter.notifyDataSetChanged();
                //listViewTelefone.setAdapter(listAdapter);

            }
        });

        btList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<listaObjTelefone.size();i++){
                    System.out.println(listaObjTelefone.get(i).getNomeTelefone()+":"+listaObjTelefone.get(i).isConectividade()+" "+listaObjTelefone.get(i).isEnergia()+" "+listaObjTelefone.get(i).isTemperatura()+" ");
                }
            }

        });



        permission();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS},1000);
        }


    }

    private void criarList() {
        if(listaObjTelefone.size()>0) {
            stubList = findViewById(R.id.viewStubList);
            stubList.inflate();
            listViewTelefone = findViewById(R.id.mylistview);
            listAdapter = new ListViewAdapter(this,R.layout.list_item,listaObjTelefone);
            listViewTelefone.setAdapter(listAdapter);
            listViewTelefone.setChoiceMode(listViewTelefone.CHOICE_MODE_MULTIPLE);

        }
    }


    public static void monitorar(List<objEsp> lista){
        List<objEsp> auxTemperatura = new ArrayList<>();
        List<objEsp> auxTensao = new ArrayList<>();
        List<objEsp> auxConectividade = new ArrayList<>();
        System.out.println("Valor do cont: "+cont);
        int num;
            for (int i = 0; i < lista.size(); i++) {
                if (Float.parseFloat(lista.get(i).getTemperatura()) < lista.get(i).getAlerta()) {
                    auxTemperatura.add(lista.get(i));
                }
                if(lista.get(i).getTensao() == 0){
                    auxTensao.add(lista.get(i));
                }
                if(lista.get(i).getStatus() > 1){
                    auxConectividade.add(lista.get(i));
                }
            }
        System.out.println("Valor auxTemperatura: "+auxTemperatura.size());
        System.out.println("Valor auxTensão: "+auxTensao.size());
        System.out.println("Valor auxConectividade: "+auxConectividade.size());

            if(auxTemperatura.size()>0 || auxConectividade.size()>0 || auxTensao.size()>0) {
                System.out.println("Valor do cont antes: "+cont);
                cont++;
                if ((cont == 1)) {
                    System.out.println("Valor do cont depois: "+cont);
                    //if (estadoSwitch == false) {
                        if (lista.size() > auxTemperatura.size()) {
                            for (int j = 0; j < auxTemperatura.size(); j++) {
                                mandarMensagem("O freeze " + auxTemperatura.get(j).getApelido() + " está com temperatura elevada", numeroFone);
                            }
                        } else {
                            for (int a = 0; a < auxTemperatura.size(); a++) {
                                mandarMensagem("Os freeze estão com temperatura elevada", numeroFone);
                            }
                        }//fim da analise de temperatura

                        if(auxTensao.size()<lista.size()){
                            for (int j = 0; j < auxTemperatura.size(); j++) {
                                mandarMensagem("O freeze " + auxTemperatura.get(j).getApelido() + " está sem energia", numeroFone);
                            }
                        }else{
                            mandarMensagem("Os freeze estão sem energia", numeroFone);
                        }//fim da analise de tensao

                        if (auxConectividade.size() < lista.size()) {
                            mandarMensagem("Alguns freezes estão sem conexão", numeroFone);
                        }else if(auxConectividade.size() == lista.size()){
                            mandarMensagem("Todos os freezes estão sem conexão", numeroFone);
                        }//fim da analise de conectividade


                        //estadoSwitch = true;

                    //}
                } else if (cont == 10) {
                    cont = 0;
                    estadoSwitch = false;
                }
            }


    }


    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this, SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }

    }

    static public void mandarMensagem(String msg, String fone) {
        try {
            SmsManager sms = SmsManager.getDefault();
            for(int i=0;i<listaObjTelefone.size();i++){
                String temp;
                temp = listaObjTelefone.get(i).getNumero();
                sms.sendTextMessage(temp, null, msg, null, null);
            }
        } catch (Exception e){

        }


    }


    public void salvarNumero(String numb,String nome){
        try {
            objTelefone objTemp = new objTelefone(nome,numb,false,false,false);

            byte[] dados;
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/telefones.txt");
            //arq.deleteOnExit();
            //arq.mkdir();
            FileOutputStream fos;
            dados = (objTemp.getNomeTelefone()+";"+objTemp.getNumero()+";"+objTemp.isConectividade()+";"+objTemp.isEnergia()+";"+objTemp.isTemperatura() +"\n").getBytes();
            fos = new FileOutputStream(arq,true);
            fos.write(dados);
            fos.flush();
            fos.close();
            System.out.println("Funcionei!!!!!");
            Log.i("teste: ", "Funcionei!!!!!");
            //listaTelefone.add(nome+": "+numb);
            Toast.makeText(this, "Número salvo com sucesso",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Erro",Toast.LENGTH_SHORT).show();
        }
    }

    public void salvarListaNumeros(){
        try {
            for(int i=0;i<listaObjTelefone.size();i++) {
                byte[] dados;
                File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/telefones.txt");
                //arq.deleteOnExit();
                //arq.mkdir();
                FileOutputStream fos;
                dados = (listaObjTelefone.get().getNomeTelefone() + ";" + objTemp.getNumero() + ";" + objTemp.isConectividade() + ";" + objTemp.isEnergia() + ";" + objTemp.isTemperatura() + "\n").getBytes();
                fos = new FileOutputStream(arq, true);
                fos.write(dados);
                fos.flush();
                fos.close();
            }
            System.out.println("Funcionei!!!!!");
            Log.i("teste: ", "Funcionei!!!!!");
            //listaTelefone.add(nome+": "+numb);
            Toast.makeText(this, "Número salvo com sucesso",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Erro",Toast.LENGTH_SHORT).show();
        }
    }

    public void lerNumero(){
        try {
            listaObjTelefone.clear();
            System.out.println("LENDO ARQUIVO DE numero telefone");
            String nome = "telefones";
            String lstrlinha;
            File arq = new File(Environment.getExternalStorageDirectory(),"/Controle_esp/"+nome+".txt");
            if(arq.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(arq));
                while ((lstrlinha = br.readLine()) != null) {
                    String[] temp = new String[5];
                    temp = lstrlinha.split(";",5);
                    System.out.println("Meu nome é: "+temp[0]);
                    objTelefone objtemp = new objTelefone(temp[0],temp[1],Boolean.parseBoolean(temp[2]),Boolean.parseBoolean(temp[3]),Boolean.parseBoolean(temp[4]));
                    listaObjTelefone.add(objtemp);
                }
            }
            System.out.println("tamanho lista: "+listaObjTelefone.size());
            for(int i=0;i<listaObjTelefone.size();i++){
                System.out.println("listaaaa "+listaObjTelefone.get(i).getNomeTelefone());
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
















    public void permission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(TelaMensagem.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(TelaMensagem.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(TelaMensagem.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(numeroFone, null, mensagem, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(getApplicationContext(),"SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    public void salvarArquivoTelefone(List lista){
        try {
            byte[] dados;
            limparArquivo("telefones");
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/telefones.txt");

            for(int a=0;a<lista.size();a++) {
                FileOutputStream fos;
                dados = (lista.get(a) + "\n").getBytes();
                fos = new FileOutputStream(arq, true);
                fos.write(dados);
                fos.flush();
                fos.close();
            }

            System.out.println("Funcionei!!!!!");
            Log.i("teste: ", "Funcionei!!!!!");
        }catch (Exception e){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getParent().getBaseContext(), "Erro",Toast.LENGTH_SHORT).show();
                }
            });
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Arquivo não encontrado",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (IOException ioe){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Erro",Toast.LENGTH_SHORT).show();
                }
            });

        }

    }


}
