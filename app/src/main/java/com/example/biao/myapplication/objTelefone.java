package com.example.biao.myapplication;

/**
 * Created by biao on 13/09/2018.
 */

public class objTelefone {

    private String nomeTelefone;
    private String numero;
    private boolean conectividade, energia, temperatura;

    public objTelefone(){}

    public String getNomeTelefone() {
        return nomeTelefone;
    }

    public objTelefone(String nomeTelefone, String numero, boolean conectividade, boolean energia, boolean temperatura){
        this.nomeTelefone = nomeTelefone;
        this.numero=numero;
        this.conectividade=conectividade;
        this.energia=energia;
        this.temperatura=temperatura;
    }

    public void setNomeTelefone(String nomeTelefone) {
        this.nomeTelefone = nomeTelefone;
    }

    public boolean isConectividade() {
        return conectividade;
    }

    public void setConectividade(boolean conectividade) {
        this.conectividade = conectividade;
    }

    public boolean isEnergia() {
        return energia;
    }

    public void setEnergia(boolean energia) {
        this.energia = energia;
    }

    public boolean isTemperatura() {
        return temperatura;
    }

    public void setTemperatura(boolean temperatura) {
        this.temperatura = temperatura;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
