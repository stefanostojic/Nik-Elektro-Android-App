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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga);

        final Button btnPretraga = (Button) findViewById(R.id.btnPretraga);

        final Context c = this.getBaseContext();

        final ListView lvRezultatiPretrage = (ListView) findViewById(R.id.lvRezultatiPretrage);

        final EditText textEditPretraga = (EditText) findViewById(R.id.editTextID);

        lvRezultatiPretrage.setVisibility(View.INVISIBLE);

        btnPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//

                ProizvodListAdapter adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, MainActivity.proizvodi);
                lvRezultatiPretrage.setAdapter(adapter);

                ArrayList<Proizvod> lista = new ArrayList<>();

                toastMessage("uneti id"+btnPretraga.getText());

                if (false) {
                    boolean flag = false;
                    Proizvod trazeniProizvod;
                    for (Proizvod p : MainActivity.proizvodi) {
                        if (p.getId().equals(textEditPretraga.getText().toString()+".0")) {
                            flag = true;
                            lista.add(p);
                        }
                        else
                            trazeniProizvod = new Proizvod("greska", "greska", "greska");
                    }

                    if (flag) {
                        //lista.add(trazeniProizvod);
                        adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, lista);
                    }
                }
                else {
                    adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, MainActivity.proizvodi);
                    toastMessage("Niste uneli ID za pretragu");
                }

                adapter = new ProizvodListAdapter(c, R.layout.adapter_view_layout, lista);
                lvRezultatiPretrage.setAdapter(adapter);

                lvRezultatiPretrage.setVisibility(View.VISIBLE);

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
