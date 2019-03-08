/*
package com.example.importingexcell;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Pretraga_Activity_V2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_v2);
    }
}
*/

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pretraga_Activity_V2 extends AppCompatActivity {

    private static final String TAG = "Pretraga_Activity123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_v2);

        final Button btnPretraga = (Button) findViewById(R.id.btnPretraga);

        final Context c = this.getBaseContext();

        final EditText textEditPretraga = (EditText) findViewById(R.id.etPretraga);

        final TextView tvIme = (TextView) findViewById(R.id.tvIme);
        final TextView tvKolicina = (TextView) findViewById(R.id.tvKolicina);

        tvIme.setVisibility(View.INVISIBLE);
        tvKolicina.setVisibility(View.INVISIBLE);

        btnPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"Dugme \"Pretraga\" je kliknuto");

                Log.d(TAG,"ListView je prikazan");

                Log.d(TAG,"MainActivity.proizvodi.get(0).getId().toString() = " + MainActivity.proizvodi.get(0).getId().toString());
                Log.d(TAG,"textEditPretraga.getText().toString() = " + textEditPretraga.getText().toString());

                Proizvod pronadjeniProizvod;

                if (textEditPretraga.getText().toString() != "") {
                    boolean uspesnoPronadjenProizvod = false;
                    for (Proizvod p : MainActivity.proizvodi) {
                        if (p.getId().equals(textEditPretraga.getText().toString())) {
                            tvIme.setVisibility(View.VISIBLE);
                            tvKolicina.setVisibility(View.VISIBLE);
                            tvIme.setText(p.getIme());
                            tvKolicina.setText(p.getKolicina());
                            uspesnoPronadjenProizvod = true;
                            toastMessage("Proizvod je pronađen");
                            Log.d(TAG,"Proizvod je pronadjen");
                            break;
                        }
                    }

                    if (!uspesnoPronadjenProizvod) {
                        toastMessage("Proizvod nije pronađen");
                        tvIme.setVisibility(View.INVISIBLE);
                        tvKolicina.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    toastMessage("Niste uneli ID za pretragu");
                }
            }
        });

    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
}

