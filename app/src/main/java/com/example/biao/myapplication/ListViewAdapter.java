package com.example.biao.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by biao on 13/09/2018.
 */

public class ListViewAdapter extends ArrayAdapter<objTelefone> {

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<objTelefone> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if(null == v){
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item,null);

            final objTelefone telef = getItem(position);
            TextView tvTelefone = v.findViewById(R.id.textViewTelefone);
            final CheckBox cbConect = v.findViewById(R.id.checkBoxConec);
            CheckBox cbEnergia = v.findViewById(R.id.checkBoxEnergia);
            CheckBox cbTemperatura = v.findViewById(R.id.checkBoxTemperatura);

            tvTelefone.setText(telef.getNomeTelefone()+": "+telef.getNumero());

            if(telef.isConectividade()==true){
                cbConect.setChecked(true);
            }else{
                cbConect.setChecked(false);
            }

            if(telef.isEnergia()==true){
                cbEnergia.setChecked(true);
            }else{
                cbEnergia.setChecked(false);
            }

            if(telef.isTemperatura()==true){
                cbTemperatura.setChecked(true);
            }else{
                cbTemperatura.setChecked(false);
            }

            cbConect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    telef.setConectividade(isChecked);
                }
            });

            cbEnergia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    telef.setEnergia(isChecked);
                }
            });

            cbTemperatura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    telef.setTemperatura(isChecked);
                }
            });

        }

        return v;
    }







}
