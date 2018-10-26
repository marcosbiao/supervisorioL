package com.example.biao.myapplication;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static com.example.biao.myapplication.LeitorServer.getJSONFromAPI;

public class TelaScan extends AppCompatActivity {

    EditText etNumeroIP, etNumeroNos;
    Button btScan;
    int i, contador = 0;

    byte[] ipBytes;
    ArrayList<String> listaIps = new ArrayList<>();
    //List<ObjEsp> listaObj = new ArrayList<>();

    //InetAddress localhost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_scan);

        etNumeroNos = findViewById(R.id.etnumeronos);
        etNumeroIP = findViewById(R.id.etnumeroip);
        btScan = findViewById(R.id.btscan);

        try {
            fazerScan();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        lerIps();
        criarObjetos();

    }


    public void fazerScan() throws UnknownHostException {
        limparArquivo("ips");
        //localhost = InetAddress.getLocalHost();
        //ip = localhost.getAddress();
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip2 = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        String ip3 = ip2.replace(".", ":");
        System.out.println("ip2: " + ip2 + "   ip3: " + ip3);
        InetAddress ip = InetAddress.getByName(ip2);
        ipBytes = ip.getAddress();

        //System.out.println("ip2: "+ip2+ "   ip: "+ ip.toString());
        String[] temp = ip3.split(":", 4);
        //for(int i= 0;i<3;i++){
        //ipBytes[i] = (byte) Integer.parseInt(temp[i]);
        //}

        for (int i = 90; i <= 130; i++) {
            ipBytes[3] = (byte) i;
            InetAddress address = InetAddress.getByAddress(ipBytes);
            String tentativa = getJSONFromAPI("http://" + temp[0] + "." + temp[1] + "." + temp[2] + "." + i);
            System.out.println(temp[0] + "." + temp[1] + "." + temp[2] + "." + i + "\n");
            if (!tentativa.equals("")) {
                salvarIps(temp[0] + "." + temp[1] + "." + temp[2] + "." + i);
                salvarIps(temp[0] + "." + temp[1] + "." + temp[2] + "." + i);
                contador++;
            }

        }
    }


    private void limparArquivo(String t) {
        //criar pasta
        File folder = new File(Environment.getExternalStorageDirectory() + "/Controle_esp");
        if (!folder.exists()) {
            folder.mkdir();
        }
        //salvar arquivo dentro da pasta
        String nomeArquivo = t + ".txt";
        File arquivo = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/Controle_esp/" + nomeArquivo);
        try {
            FileOutputStream salvar = new FileOutputStream(arquivo);
            String conteudo = "";
            salvar.write(conteudo.getBytes());
            salvar.close();
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Arquivo não encontrado", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }

    }


    public void salvarIps(String ip) {
        try {
            byte[] dados;
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/ips.txt");
            FileOutputStream fos;
            dados = (ip + "\n").getBytes();
            fos = new FileOutputStream(arq, true);
            fos.write(dados);
            fos.flush();
            fos.close();
            System.out.println("Funcionei!!!!!");
            Log.i("teste: ", "Funcionei!!!!!");
        } catch (Exception e) {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }

    public void salvarDadosEsp(ObjEsp oe) {
        try {
            byte[] dados;
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + oe.getMac() + ".txt");
            FileOutputStream fos;
            limparArquivo(oe.getMac());
            dados = (oe.getMac() + "\n" + oe.getIp() + "\n" + oe.getApelido() + "\n" + oe.getAlerta() + "\n").getBytes();
            fos = new FileOutputStream(arq, true);
            fos.write(dados);
            fos.flush();
            fos.close();
            //System.out.println("GRAVEI!!!!!");
            //Log.i("teste: ", "GRAVEI!!!!!");
        } catch (Exception e) {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }


    public void lerIps() {
        try {
            String nome = "ips";
            String lstrlinha;
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + nome + ".txt");
            BufferedReader br = new BufferedReader(new FileReader(arq));
            while ((lstrlinha = br.readLine()) != null) {
                listaIps.add(lstrlinha);
                //System.out.println("Lendo lista"+listaIps.get(i));
                //Log.i("teste: ","Lendo lista"+ listaIps.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjEsp lerObjeto(String ip) {
        ObjEsp temp = new ObjEsp();
        String retorno = getJSONFromAPI("http://" + ip);

        String[] sp = retorno.split(";", 4);
        /*
        for(int i=0;i<4;i++){
            System.out.println("JOGANDO NA LISTA:  "+ sp[i]);
            Log.i("JOGANDO NA LISTA:   ", sp[i]);
        }
        */
        temp.setTemperatura(sp[0]);
        temp.setVoltagem(sp[1]);
        temp.setAtividade(sp[2]);
        temp.setMac(sp[3]);
        temp.setIp(ip);
        temp.setApelido("lol lol");


        return temp;
    }

    public void criarObjetos() {

        //listaObj.clear();
        List<ObjEsp> listaTemporariaObj = new ArrayList<>();
        System.out.println("Quantidade de IPS: " + listaIps.size());
        for (i = 0; i < listaIps.size(); i++) {

            ObjEsp temp = lerObjeto(listaIps.get(i));
            File arq = new File(Environment.getExternalStorageDirectory() + "/Controle_esp/" + temp.getMac() + ".txt");

            if (comparador(temp)) {
                salvarDadosEsp(buscador(temp));
                listaTemporariaObj.add(buscador(temp));
                //listaObj.add(temp);
            } else {
                salvarDadosEsp(temp);
                listaTemporariaObj.add(temp);
            }
            //System.out.println("JOGANDO NA LISTA de objetos:  "+temp.getMac());
            //Log.i("JOGANDO NA LISTA:   ", "JOGANDO NA LISTA de objetos:");


        }

        TelaPrincipal.espList = listaTemporariaObj;
        for (int k = 0; k < MainActivity.listaObj.size(); k++) {
            System.out.println(MainActivity.listaObj.get(k).getMac() + " " + MainActivity.listaObj.get(k).getApelido());
        }

    }

    public ObjEsp buscador(ObjEsp ob) {
        ObjEsp ativo = new ObjEsp();
        String lstrlinha;
        try {
            File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + ob.getMac() + ".txt");
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
            ativo.setMac(ob.getMac());
            ativo.setIp(ob.getIp());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ativo;
    }


    public boolean comparador(ObjEsp ob) {
        boolean a = false;
        File arq = new File(Environment.getExternalStorageDirectory(), "/Controle_esp/" + ob.getMac() + ".txt");
        if (arq.exists()) {
            a = true;
            System.out.println("ACHEI IGUAL");
        }


        return a;
    }


}
