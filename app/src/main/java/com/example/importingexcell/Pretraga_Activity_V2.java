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
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Pretraga_Activity_V2 extends AppCompatActivity {

    String[] elementiDirektorijuma= MainActivity.lastDirectory.split("/");
    String direktorijum="";
     public static Proizvod pronadjeniProizvod;
    private static final String TAG = "Pretraga_Activity123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_v2);

        final Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        final Button btnPretraga = (Button) findViewById(R.id.btnPretraga);
        final Context c = this.getBaseContext();

        final EditText textEditPretraga = (EditText) findViewById(R.id.etPretraga);

        final EditText editTextKolicina = (EditText) findViewById(R.id.editTextKolicina);

        final TextView tvIme = (TextView) findViewById(R.id.tvIme);
        final TextView tvKolicina = (TextView) findViewById(R.id.tvKolicina);

        tvIme.setVisibility(View.INVISIBLE);
        editTextKolicina.setVisibility(View.INVISIBLE);

        for(int i=0;i<elementiDirektorijuma.length-1;i++)
        {
            direktorijum=direktorijum+elementiDirektorijuma[i]+"/";
        }
        


        btnPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"Dugme \"Pretraga\" je kliknuto");


                Log.d(TAG,"ListView je prikazan");

                Log.d(TAG,"MainActivity.proizvodi.get(0).getId().toString() = " + MainActivity.proizvodi.get(0).getId().toString());
                Log.d(TAG,"textEditPretraga.getText().toString() = " + textEditPretraga.getText().toString());

                if (textEditPretraga.getText().toString() != "") {
                    boolean uspesnoPronadjenProizvod = false;
                    for (Proizvod p : MainActivity.proizvodi) {
                        if (p.getId().equals(textEditPretraga.getText().toString())) {
                            tvIme.setVisibility(View.VISIBLE);
                            editTextKolicina.setVisibility(View.VISIBLE);
                            tvIme.setText(p.getIme());
                            editTextKolicina.setText(p.getKolicina());
                            pronadjeniProizvod=p;
                            uspesnoPronadjenProizvod = true;
                            toastMessage("Proizvod je pronađen");
                            Log.d(TAG,"Proizvod je pronadjen");
                            break;
                        }
                    }

                    if (!uspesnoPronadjenProizvod) {
                        toastMessage("Proizvod nije pronađen");
                        tvIme.setVisibility(View.INVISIBLE);
                        editTextKolicina.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    toastMessage("Niste uneli ID za pretragu");
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cuvamo indeks iz liste
                if(editTextKolicina.getText().toString().isEmpty())
                {
                    toastMessage("Polje za kolicinu je prazno.");
                }
                else {
                    int brojReda = MainActivity.proizvodi.indexOf(pronadjeniProizvod) + 1;
                    writeSheet(editTextKolicina.getText().toString(), brojReda);
                    pronadjeniProizvod.setKolicina(editTextKolicina.getText().toString());
                    toastMessage("Promena je sacuvana");
                    logovanje();
                }
            }
        });

    }

    public static void writeSheet(String data,int i) {
        HSSFWorkbook workbook;

        try {
            String outFileName = "filebook.xls";

            File path = new File(MainActivity.lastDirectory);
            Log.d(TAG,MainActivity.lastDirectory);

            FileInputStream file = new FileInputStream(path);

            workbook = new HSSFWorkbook(file);
            Log.d(TAG,"napravio workbook");

            //Row row = workbook.getSheetAt(0).createRow(0);
            Cell cell = workbook.getSheetAt(0).getRow(i).getCell(6);
            cell.setCellValue(Integer.parseInt(data));

            file.close();

            FileOutputStream outFile =new FileOutputStream(path);
            workbook.write(outFile);
            outFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    private void logovanje(){

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time


        File file = new File(direktorijum,"logPromena.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.append(formattedDate + ": Sifra: " + pronadjeniProizvod.getId() + ", Ime:" + pronadjeniProizvod.getIme() + ", Kolicina:" + pronadjeniProizvod.getKolicina() + "\n");
            writer.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

