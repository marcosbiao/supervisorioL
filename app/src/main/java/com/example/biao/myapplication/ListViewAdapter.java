package com.example.biao.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import static com.example.biao.myapplication.TelaMensagem.listaObjTelefone;

/**
 * Created by biao on 13/09/2018.
 */

public class ListViewAdapter extends ArrayAdapter<objTelefone> {

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<objTelefone> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);

            final objTelefone telef = getItem(position);
            TextView tvTelefone = v.findViewById(R.id.textViewTelefone);
            final CheckBox cbConect = v.findViewById(R.id.checkBoxConec);
            CheckBox cbEnergia = v.findViewById(R.id.checkBoxEnergia);
            CheckBox cbTemperatura = v.findViewById(R.id.checkBoxTemperatura);
            ImageButton btExcluir = v.findViewById(R.id.imageButtonExcluir);

            tvTelefone.setText(telef.getNomeTelefone() + ": " + telef.getNumero());

            if (telef.isConectividade() == true) {
                cbConect.setChecked(true);
            } else {
                cbConect.setChecked(false);
            }

            if (telef.isEnergia() == true) {
                cbEnergia.setChecked(true);
            } else {
                cbEnergia.setChecked(false);
            }

            if (telef.isTemperatura() == true) {
                cbTemperatura.setChecked(true);
            } else {
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

            final int posicao = position;

            btExcluir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int p = posicao;
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    //define o titulo
                    builder.setTitle(listaObjTelefone.get(posicao).getNomeTelefone());
                    //define a mensagem
                    builder.setMessage("Deseja excluir esse número?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listaObjTelefone.remove(posicao);
                            TelaMensagem.salvarListFora();
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create();
                    builder.show();
                }
            });


        }

        return v;
    }


}
