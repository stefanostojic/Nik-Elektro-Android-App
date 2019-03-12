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

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.w3c.dom.Text;

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
    public static File file;
    public int proslaKolicina,novaKolicina;

   // SearchView svPretraga = (SearchView) findViewById(R.id.svPretraga);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretraga_v2);
        toastMessage("Podaci su učitani");
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        final Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        final Button btnPretraga = (Button) findViewById(R.id.btnPretraga);
        final TextView tvIme = (TextView) findViewById(R.id.tvIme);
        final SearchView searchViewPretraga = (SearchView) findViewById(R.id.etPretraga); // prethodno: editTextPretraga, takođe, sad se umesto getText() piše getQuery()
        final EditText editTextUvecajZa = (EditText) findViewById(R.id.editTextUvecajZa);
        final EditText editTextTrenutnaKolicina = (EditText) findViewById(R.id.editTextTrenutnaKolicina);

        //postavlja veličinu teksta polja za pretragu
        LinearLayout linearLayout1 = (LinearLayout) searchViewPretraga.getChildAt(0);
        LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(2);
        LinearLayout linearLayout3 = (LinearLayout) linearLayout2.getChildAt(1);
        AutoCompleteTextView autoComplete = (AutoCompleteTextView) linearLayout3.getChildAt(0);
        autoComplete.setTextSize(24);

        //sakriva polja o pronađenom proizvodu pri prvom paljenju aplikacije
        tvIme.setVisibility(View.INVISIBLE);
        editTextUvecajZa.setVisibility(View.INVISIBLE);
        editTextTrenutnaKolicina.setVisibility(View.INVISIBLE);

        for(int i = 0; i < elementiDirektorijuma.length - 1; i++)
        {
            direktorijum = direktorijum + elementiDirektorijuma[i] + "/";

        }

        btnPretraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"Dugme \"Pretraga\" je kliknuto");


                Log.d(TAG,"ListView je prikazan");

                if (searchViewPretraga.getQuery().toString() != "") {
                    boolean uspesnoPronadjenProizvod = false;
                    for (Proizvod p : MainActivity.proizvodi) {
                        if (p.getId().equals(searchViewPretraga.getQuery().toString())) {
                            tvIme.setVisibility(View.VISIBLE);
                            editTextUvecajZa.setVisibility(View.VISIBLE);
                            editTextTrenutnaKolicina.setVisibility(View.VISIBLE);
                            editTextUvecajZa.requestFocus();
                            tvIme.setText(p.getIme());
                            /*editTextUvecajZa.setText(p.getKolicina());*/
                            editTextTrenutnaKolicina.setText(p.getKolicina());
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
                        editTextUvecajZa.setVisibility(View.INVISIBLE);
                        editTextTrenutnaKolicina.setVisibility(View.INVISIBLE);
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
                if(editTextUvecajZa.getText().toString().isEmpty())
                {
                    toastMessage("Polje za količinu je prazno");
                }
                else if (editTextUvecajZa.getText().toString() == pronadjeniProizvod.getKolicina())
                {
                    toastMessage("Polje za količinu je nepromenjeno");
                }
                else if(editTextTrenutnaKolicina.getText().toString().isEmpty())
                {
                    toastMessage("Proizvod nije odabran");
                }
                else
                {
                    Upis upis = new Upis();
                    upis.execute();


                    toastMessage("Promena je sačuvana");


                    searchViewPretraga.requestFocus();

                    int uvecajOd = Integer.parseInt(editTextTrenutnaKolicina.getText().toString());
                    int uvecajDo = uvecajOd + Integer.parseInt(editTextUvecajZa.getText().toString());
                    editTextUvecajZa.setText("");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            editTextTrenutnaKolicina.setTypeface(null, Typeface.NORMAL);
                            searchViewPretraga.setQuery("",false);
                            tvIme.setVisibility(View.INVISIBLE);
                            editTextUvecajZa.setVisibility(View.INVISIBLE);
                            editTextTrenutnaKolicina.setVisibility(View.INVISIBLE);
                        }
                    }, 3000);

                    animateEditText(uvecajOd, uvecajDo, ((EditText) findViewById(R.id.editTextTrenutnaKolicina)));
                }
            }
        });
    }

    public void animateEditText(int uvecajOd, int uvecajDo, final EditText editText) {
        
        ValueAnimator valueAnimator = ValueAnimator.ofInt(uvecajOd, uvecajDo);
        valueAnimator.setDuration(1250);

        editText.setTypeface(null, Typeface.BOLD);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                editText.setText(valueAnimator.getAnimatedValue().toString());

            }
        });

        valueAnimator.start();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                File fileLog= new File(direktorijum,"logPromena.txt");
                Uri path = Uri.fromFile(fileLog);
                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenintent.setDataAndType(path, "text/plain");
                try {
                    startActivity(pdfOpenintent);
                }
                catch (ActivityNotFoundException e) {

                }
                return true;
            case R.id.item3:
                showDialog(getCurrentFocus());
                return true;
            case R.id.item2:
                Intent intentListaSvihProizvoda = new Intent(getApplicationContext(), Pretraga_Activity.class);
                startActivity(intentListaSvihProizvoda);
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public class Upis extends AsyncTask {

        @Override
        protected String doInBackground(Object[] objects) {

                EditText editTextUvecajZa1 = (EditText) findViewById(R.id.editTextUvecajZa);
                int brojReda = MainActivity.proizvodi.indexOf(pronadjeniProizvod) + 1;
                int staraKolicina= Integer.parseInt(pronadjeniProizvod.getKolicina());
                int novaKolicina= Integer.parseInt(editTextUvecajZa1.getText().toString());
                pronadjeniProizvod.setKolicina(String.valueOf(staraKolicina+novaKolicina));
                writeSheet(pronadjeniProizvod.getKolicina(),brojReda);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            logovanje();
        }
    }

    public static void writeSheet(String data, int i) {
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
            cell.setCellValue(data);

            file.close();

            FileOutputStream outFile =new FileOutputStream(path);
            workbook.write(outFile);
            outFile.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toastMessage(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void logovanje(){

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time


        file = new File(direktorijum,"logPromena.txt");
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

    public void showDialog(View view) {
        ONamaDialog onamadialog = new ONamaDialog();
        onamadialog.show(getSupportFragmentManager(),"O nama");
    }

    /*@Override
    public void onBackPressed() {
        finish();
    }*/
}















