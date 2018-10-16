package com.example.biao.myapplication;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by biao on 20/08/2018.
 */

public class Monitoramento {

    static boolean estadoSwitch = true;
    static int cont = 0;
    static String numeroFone;


    static public void monitorar(ArrayList<objEsp> lista) {
        List<objEsp> auxTemperatura = new ArrayList<>();
        List<objEsp> auxTensao = new ArrayList<>();
        System.out.println("Valor do cont: " + cont);
        int num;
        for (int i = 0; i < lista.size(); i++) {
            if (Integer.parseInt(lista.get(i).getTemperatura()) < lista.get(i).getAlerta()) {
                auxTemperatura.add(lista.get(i));
            }
                /*if(Float.parseFloat(lista.get(i).getVoltagem()) < 5){
                    auxTensao.add(lista.get(i));
                }*/
        }

        if ((cont == 0)) {
            cont++;
            if (estadoSwitch == true) {
                if (lista.size() > auxTemperatura.size()) {
                    for (int j = 0; j < auxTemperatura.size(); j++) {
                        mandarMensagem("O freeze " + auxTemperatura.get(j).getApelido() + " está com temperatura elevada", numeroFone);
                    }
                } else {
                    for (int a = 0; a < auxTemperatura.size(); a++) {
                        mandarMensagem("Freeze" + auxTemperatura.get(a).getApelido() + " está com temperatura elevada", numeroFone);
                    }
                }


                if (cont == 100) {
                    cont = 0;
                }
            }
        } else {
            //tb.setChecked(false);
        }


    }


    static public void mandarMensagem(String msg, String fone) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(fone, null, msg, null, null);
    }


}
