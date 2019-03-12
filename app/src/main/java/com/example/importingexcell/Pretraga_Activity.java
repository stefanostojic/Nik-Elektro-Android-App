package com.example.importingexcell;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pretraga_Activity extends AppCompatActivity {

    private static final String TAG = "Pretraga_Activity123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga);
        setTitle("Lista svih proizvoda");

        final Button btnPretraga = (Button) findViewById(R.id.btnPretraga);

        final Context c = this.getBaseContext();

        final ListView lvRezultatiPretrage = (ListView) findViewById(R.id.lvRezultatiPretrage);

        final EditText textEditPretraga = (EditText) findViewById(R.id.editTextID);

        lvRezultatiPretrage.setVisibility(View.INVISIBLE);

        btnPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"Dugme \"Pretraga\" je kliknuto");

                ProizvodListAdapter adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, MainActivity.proizvodi);
                lvRezultatiPretrage.setAdapter(adapter);
                lvRezultatiPretrage.setVisibility(View.VISIBLE);

                Log.d(TAG,"ListView je prikazan");

                Log.d(TAG,"MainActivity.proizvodi.get(0).getId().toString() = " + MainActivity.proizvodi.get(0).getId().toString());
                Log.d(TAG,"textEditPretraga.getText().toString() = " + textEditPretraga.getText().toString());

                try {
                    Log.d(TAG, "POPRAVLJEN MainActivity.proizvodi.get(0).getId().toString().split(\"\\.\")[0] = " + MainActivity.proizvodi.get(0).getId().toString().split("\\.")[0]);
                }
                catch (Exception e) {
                    Log.d(TAG, "ID nije mogao da se splituje: " + e.getCause());
                    String idSaDecimalom = MainActivity.proizvodi.get(0).getId();
                    Log.d(TAG, "idSaDecimalom.split(\".\").length: " + idSaDecimalom.split(".").length);
                }

//                Log.d(TAG,"" + textEditPretraga.text);
                ArrayList<Proizvod> trazeniProizvod = new ArrayList<>();

                if (textEditPretraga.getText().toString() != "") {
                    boolean uspesnoPronadjenProizvod = false;
                    //Proizvod trazeniProizvod;
                    for (Proizvod p : MainActivity.proizvodi) {
                        /*if (p.getId().split("\\.")[0].equals(textEditPretraga.getText().toString())) {*/
                        if (p.getId().contains(textEditPretraga.getText().toString())) {
                            uspesnoPronadjenProizvod = true;
                            trazeniProizvod.add(p);
                            Log.d(TAG,"Proizvod je pronadjen");
//                            break;
                        }
                        else
                        {
                            //trazeniProizvod.add(new Proizvod("greska", "greska", "greska"));
                        }
                    }

                    if (uspesnoPronadjenProizvod) {
                        lvRezultatiPretrage.setAdapter(null);
                        lvRezultatiPretrage.setAdapter(new ProizvodListAdapter(c, R.layout.adapter_view_layout, trazeniProizvod));
//                        adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, trazeniProizvod);
//                        adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, MainActivity.proizvodi);
                    }
                    else {
                        toastMessage("Proizvod nije pronađen");
                    }
                }
                else {
                    adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, MainActivity.proizvodi);
                    toastMessage("Niste uneli ID za pretragu");
                }

                /*adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, lista);
                lvRezultatiPretrage.setAdapter(adapter);

                lvRezultatiPretrage.setVisibility(View.VISIBLE);*/

            }
        });

        lvRezultatiPretrage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Proizvod p = (Proizvod) lvRezultatiPretrage.getItemAtPosition(position);
                toastMessage(p.getIme().toString());
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}
